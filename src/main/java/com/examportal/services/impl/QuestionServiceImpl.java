package com.examportal.services.impl;

import com.examportal.dto.OptionDTO;
import com.examportal.dto.QuestionDTO;
import com.examportal.dto.QuizDTO;
import com.examportal.dto.ResponseDTO;
import com.examportal.models.ERole;
import com.examportal.models.Option;
import com.examportal.models.Question;
import com.examportal.models.Quiz;
import com.examportal.repository.QuestionRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.services.QuestionService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public ResponseDTO<Question> saveQuestion(QuestionDTO questionDTO) {
        boolean hasAnswer = questionDTO.getOptionDTOList().stream().anyMatch(OptionDTO::getIsCorrect);
       if(hasAnswer){
           Optional<Quiz> quiz = quizRepository.findById(questionDTO.getQuizId());
           return new ResponseDTO<>(true, "Quiz Saved Successfully.", quiz.map(val-> questionRepository.save(
                   new Question(null, questionDTO.getName(), questionDTO.getOptionDTOList().stream().map(optionDTO -> new Option(optionDTO.getId(), optionDTO.getName(), optionDTO.getIsCorrect())).collect(Collectors.toList()), val, false))).orElseThrow(()-> new IllegalArgumentException("Quiz not found with Quiz ID: "+ questionDTO.getQuizId())));

       }
       return new ResponseDTO<>(false, "Please Select Answer.", null);
    }

    @Override
    public ResponseDTO<List<Question>> importQuestions(Integer quizId, MultipartFile file) {
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            if(isImportQuestionsTemplateValid(rowIterator.next())){
                if(sheet.getPhysicalNumberOfRows() == 1){
                    return new ResponseDTO<>(false, "No data found.", null);
                }
                Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("Quiz not found with Quiz ID: "+quizId));
                List<Question> questionList = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    String questionName = row.getCell(0) != null ? row.getCell(0).getStringCellValue().trim() : "";
                    String answer = row.getCell(5) != null ? row.getCell(5).getStringCellValue().trim() : "";
                    String optionA = row.getCell(1) !=null ? row.getCell(1).getStringCellValue().trim() : "";
                    String optionB = row.getCell(2) !=null ? row.getCell(2).getStringCellValue().trim() : "";
                    String optionC = row.getCell(3) !=null ? row.getCell(3).getStringCellValue().trim() : "";
                    String optionD = row.getCell(4) != null ? row.getCell(4).getStringCellValue().trim() : "";
                    List<Option> optionList= Arrays.asList(
                            new Option(null, optionA, optionA.equals(answer)),
                            new Option(null,  optionB, optionB.equals(answer)),
                            new Option(null, optionC, optionC.equals(answer)),
                            new Option(null, optionD, optionD.equals(answer))
                    );

                    int rowNumber = row.getRowNum()+1;

                    if(questionName.isEmpty() && optionList.stream().allMatch(option -> option.getName().isEmpty()) && answer.isEmpty()){
                        continue;
                    }
                    else if (questionName.isEmpty()){
                        return new ResponseDTO<>(false, "Question is required, Row: "+rowNumber, null);
                    }else if(optionList.stream().anyMatch(option -> option.getName().trim().isEmpty())){
                        return new ResponseDTO<>(false, "Options can't be empty, Row: "+rowNumber, null);
                    }else if(answer.isEmpty()){
                        return new ResponseDTO<>(false, "Answer can't be empty, Row: "+rowNumber, null);
                    }else if(!areAllOptionsUnique(optionList)){
                        return new ResponseDTO<>(false, "Options should be unique, Row: "+rowNumber, null);
                    }else  if(optionList.stream().noneMatch(Option::getIsCorrect)){
                        return new ResponseDTO<>(false, "Answer does not match with options, Row: "+rowNumber, null);
                    }

                    Question question = new Question(
                            null,
                            questionName,
                            optionList,
                            quiz,
                            true
                    );
                    questionList.add(question);
                }
                workbook.close();
                return new ResponseDTO<>(true, "Questions imported successfully.",  questionRepository.saveAll(questionList));
            }
            return new ResponseDTO<>(false, "Invalid template.", null);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public QuizDTO getAllQuestions(Integer quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(quizOptional.isPresent()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            List<Question> questions = questionRepository.findByQuiz(quizOptional.get(), Sort.by(Sort.Direction.DESC, "id"));

            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setName(quizOptional.get().getName());

            if(authentication.getAuthorities().stream().anyMatch(a-> a.getAuthority().equals("ROLE_"+ ERole.ADMIN.name()))){
                quizDTO.setQuestionDTOList(questions.stream().map(question -> new QuestionDTO(question.getId(), question.getName(), null, question.getOptionList().stream().map(option -> new OptionDTO(option.getId(), option.getName(), option.getIsCorrect())).collect(Collectors.toList()), question.getIsActive())).collect(Collectors.toList()));
            }
            else {
                quizDTO.setQuestionDTOList(questions.stream().map(question -> new QuestionDTO(question.getId(), question.getName(), null, question.getOptionList().stream().map(option -> new OptionDTO(option.getId(), option.getName(), false)).collect(Collectors.toList()), question.getIsActive())).collect(Collectors.toList()));
            }
            return quizDTO;
        }
        throw new IllegalArgumentException("Quiz Not Found With Quiz Id: "+quizId);
    }

    private boolean areAllOptionsUnique(List<Option> options){
        HashSet<String> stringHashSet = new HashSet<>();
        for(Option option : options){
            if(!stringHashSet.add(option.getName())){
                return false;
            }
        }
        return true;
    }

    private boolean isImportQuestionsTemplateValid(Row header){
        List<String> expectedHeaders = List.of("Question", "Option A", "Option B", "Option C", "Option D", "Answer");
        for(int i=0; i< expectedHeaders.size(); i++){
            if(!expectedHeaders.get(i).equals(header.getCell(i)!=null ? header.getCell(i).getStringCellValue() : "")){
                return false;
            }
        }
        return true;
    }
}
