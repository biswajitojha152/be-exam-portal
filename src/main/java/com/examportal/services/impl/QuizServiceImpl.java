package com.examportal.services.impl;

import com.examportal.dto.*;
import com.examportal.dto.projection.QuizIdsWithQuizCountProjection;
import com.examportal.dto.projection.QuizProjection;
import com.examportal.dto.projection.QuizProjectionWithQuestionCount;
import com.examportal.helper.JsonConverter;
import com.examportal.models.*;
import com.examportal.repository.CategoryRepository;
import com.examportal.repository.QuizRepository;
import com.examportal.repository.QuizTrailRepository;
import com.examportal.repository.UserRepository;
import com.examportal.services.AuditLogService;
import com.examportal.services.QuizService;
import com.examportal.session.InMemoryQuizProgressStore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizTrailRepository quizTrailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private InMemoryQuizProgressStore inMemoryQuizProgressStore;

    @Override
    public PaginatedResponse<QuizDTO> getAllQuiz(Integer pageNo, Integer pageSize, Integer categoryId, String searchInput) {
        if(categoryId == null  || categoryRepository.existsById(categoryId)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_"+ERole.ADMIN.name()));
            PaginatedResponse<QuizDTO> paginatedResponse = new PaginatedResponse<>();
                if(pageNo==0 && pageSize==0){
                    if(isAdmin){
                        List<QuizProjectionWithQuestionCount> quizProjectionsList = quizRepository.findQuizListWithQuestionCount(searchInput, categoryId, Sort.by(Sort.Direction.DESC, "id"));
                        paginatedResponse.setData(quizProjectionsList.stream().map(quizProjection -> new QuizDTO(quizProjection.getId(), quizProjection.getName(), quizProjection.getCategoryId(),quizProjection.getCategoryName(), null, quizProjection.getDescription(), new QuizQuestionCountDTO(quizProjection.getTotalQuestionCount(), quizProjection.getActiveQuestionCount(), quizProjection.getInActiveQuestionCount()), quizProjection.getAttemptableCount(), quizProjection.getDuration(), quizProjection.getIsActive())).collect(Collectors.toList()));
                        paginatedResponse.setPageSize(quizProjectionsList.size());
                        paginatedResponse.setTotalElements(quizProjectionsList.size());
                    }else {
                        List<QuizProjection> quizList= quizRepository.findQuizList(searchInput, categoryId, Sort.by(Sort.Direction.DESC, "id"));
                        paginatedResponse.setData(quizList.stream().map(quizProjection -> new QuizDTO(quizProjection.getId(), quizProjection.getName(), null, quizProjection.getCategoryName(), null, quizProjection.getDescription(), null, quizProjection.getAttemptableCount(), quizProjection.getDuration(), false)).collect(Collectors.toList()));
                        paginatedResponse.setPageSize(quizList.size());
                        paginatedResponse.setTotalElements(quizList.size());
                    }
                    paginatedResponse.setIsLastPage(true);
                    paginatedResponse.setPageNumber(0);
                    paginatedResponse.setTotalPages(1);
                }else {
                    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
                    if(isAdmin){
                        Page<QuizProjectionWithQuestionCount> page = quizRepository.findQuizListWithQuestionCount(searchInput, categoryId, pageable);
                        paginatedResponse.setData(page.getContent().stream().map(quizProjection -> new QuizDTO(quizProjection.getId(), quizProjection.getName(), quizProjection.getCategoryId(),quizProjection.getCategoryName(), null, quizProjection.getDescription(), new QuizQuestionCountDTO(quizProjection.getTotalQuestionCount(), quizProjection.getActiveQuestionCount(), quizProjection.getInActiveQuestionCount()), quizProjection.getAttemptableCount(), quizProjection.getDuration(), quizProjection.getIsActive())).collect(Collectors.toList()));
                        paginatedResponse.setIsLastPage(page.isLast());
                        paginatedResponse.setPageNumber(page.getNumber());
                        paginatedResponse.setPageSize(page.getSize());
                        paginatedResponse.setTotalElements(page.getTotalElements());
                        paginatedResponse.setTotalPages(page.getTotalPages());
                    }else {
                       Page<QuizProjection> page = quizRepository.findQuizList(searchInput, categoryId, pageable);
                        paginatedResponse.setData(page.getContent().stream().map(quizProjection -> new QuizDTO(quizProjection.getId(), quizProjection.getName(), null,quizProjection.getCategoryName(), null, quizProjection.getDescription(), null, quizProjection.getAttemptableCount(), quizProjection.getDuration(), false)).collect(Collectors.toList()));
                        paginatedResponse.setIsLastPage(page.isLast());
                        paginatedResponse.setPageNumber(page.getNumber());
                        paginatedResponse.setPageSize(page.getSize());
                        paginatedResponse.setTotalElements(page.getTotalElements());
                        paginatedResponse.setTotalPages(page.getTotalPages());
                    }

                }
            return paginatedResponse;
        }

        throw new IllegalArgumentException("Category not found with id: "+ categoryId);
    }

    @Override
    @CacheEvict(value = "categories",allEntries = true)
    public ResponseDTO<Quiz> saveQuiz(QuizDTO quizDTO) {
        if(quizRepository.existsByName(quizDTO.getName())){
            return new ResponseDTO<>(false, "Quiz name already exists.", null);
        }

        Optional<Category> category = categoryRepository.findById(quizDTO.getCategoryId());
        if(category.isPresent()){
            Quiz result = quizRepository.save(new Quiz(null, quizDTO.getName(), category.get(), null, null,  quizDTO.getDescription(), quizDTO.getAttemptableCount(), quizDTO.getDuration(), true));
            auditLogService.saveAuditLog(EOperation.CREATE, new Quiz(result.getId(), result.getName(), new Category(category.get().getId(), category.get().getName(), null, null, category.get().getIsActive()), null, null, result.getDescription(), result.getAttemptableCount(), result.getDuration(), result.getIsActive()));
            return new ResponseDTO<>(true, "Quiz saved successfully.", result);
        }
        throw new IllegalArgumentException("Category does not exists.");
    }

    @Override
    public ResponseDTO<Quiz> updateQuiz(QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findById(quizDTO.getId()).orElseThrow(()-> new IllegalArgumentException("Quiz id does not exists."));
        if(hasChanges(quiz, quizDTO)){
            quiz.setDescription(quizDTO.getDescription());
            quiz.setAttemptableCount(quizDTO.getAttemptableCount());
            quiz.setDuration(quizDTO.getDuration());
            Quiz result = quizRepository.save(quiz);
            auditLogService.saveAuditLog(EOperation.UPDATE, new Quiz(result.getId(), result.getName(), new Category(null, quiz.getCategory().getName(), null, null,quiz.getCategory().getIsActive()), null, null, result.getDescription(), result.getAttemptableCount(), result.getDuration(), result.getIsActive()));
            return new ResponseDTO<>(true, "Quiz updated successfully.", result);
        }else {
            return new ResponseDTO<>(true, "No changes were made as the data is already up to date.", null);
        }
    }

    @Override
    public QuizTrailDTO submitQuiz() {
        Instant currentInstant = Instant.now();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        QuizProgressDTO quizProgressDTO = inMemoryQuizProgressStore.getQuizProgressForUser(username);
        if(quizProgressDTO != null){
            Quiz quiz = quizRepository.findById(quizProgressDTO.getId()).orElseThrow(()-> new IllegalArgumentException("Quiz not exits with id: "+quizProgressDTO.getId()));

            List<Question> questionList = quiz.getQuestions();

            Map<Integer, Question> questionMap = new HashMap<>();
            questionList.forEach(question -> {
                questionMap.put(question.getId(), question);
            });
            AtomicReference<Integer> correctAnswers = new AtomicReference<>(0);
            AtomicReference<Integer> attemptedQuestions = new AtomicReference<>(0);
            quizProgressDTO.getQuizProgressQuestionDTOList().forEach(questionDTO -> {
                Optional<Question> questionOptional = Optional.ofNullable(questionMap.get(questionDTO.getId()));
                if(questionOptional.isPresent()){
                    Option correctOption = questionOptional.get().getOptionList().stream().filter(Option::getIsCorrect).findFirst().orElseThrow(()-> new RuntimeException("No Correct Answer Found."));
                    Optional<OptionDTO> selectedOption = questionDTO.getOptionDTOList().stream().filter(OptionDTO::getIsCorrect).findFirst();
                    if(selectedOption.isPresent()){
                        attemptedQuestions.getAndSet(attemptedQuestions.get() + 1);
                    }
                    if(selectedOption.isPresent() && Objects.equals(selectedOption.get().getId(), correctOption.getId())){
                        correctAnswers.getAndSet(correctAnswers.get() + 1);
                    }
                }else {
                    throw new IllegalArgumentException("Question does not exits with id: "+questionDTO.getId());
                }
            });

            long timeTaken = Duration.between(quizProgressDTO.getQuizStartTime(), currentInstant).getSeconds();
            QuizTrail quizTrail = new QuizTrail(null, quiz, userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new IllegalArgumentException("No user found with this username.")), quiz.getAttemptableCount().intValue(), attemptedQuestions.get(), correctAnswers.get(), Instant.now(), (short) ((timeTaken > (quiz.getDuration()*60)) ? quiz.getDuration() * 60: timeTaken), isPassed(quiz.getAttemptableCount().intValue(), correctAnswers.get()) ? EStatus.PASSED : EStatus.FAILED);
            QuizTrail quizTrailResponse = quizTrailRepository.save(quizTrail);
            inMemoryQuizProgressStore.removeQuizForUser(username);
            return new QuizTrailDTO(quizTrailResponse.getId(), new QuizDTO(quizTrailResponse.getQuiz().getId() ,quizTrailResponse.getQuiz().getName(), null,quizTrailResponse.getQuiz().getCategory().getName(), null, null, null, quizTrailResponse.getQuiz().getAttemptableCount(), quizTrailResponse.getQuiz().getDuration(), true), username, quizTrail.getTotalQuestions(), quizTrailResponse.getAttemptedQuestions(),quizTrailResponse.getCorrectAnswer(), quizTrailResponse.getAttemptedAt(), quizTrailResponse.getTimeTaken(), quizTrailResponse.getStatus().getRoleValue());
        }
        throw new IllegalArgumentException("No Quiz In Progress Found.");
    }


    @Override
    @CacheEvict(value = "categories",allEntries = true)
    public ResponseDTO<List<Quiz>> updateQuizzesStatus(UpdateEntitiesStatusDTO<Integer> updateEntitiesStatusDTO) {
        List<Quiz> quizzes = quizRepository.findAllById(updateEntitiesStatusDTO.getIds());
        EntitiesStatusUpdateAuditDTO<Quiz> entitiesStatusUpdateAuditDTO = new EntitiesStatusUpdateAuditDTO<>();
        for(Quiz quiz : quizzes){
            quiz.setIsActive(updateEntitiesStatusDTO.getIsActive());
        }
        List<Quiz> resultList = quizRepository.saveAll(quizzes);
        entitiesStatusUpdateAuditDTO.setRemark(updateEntitiesStatusDTO.getRemark().trim());
        List<Quiz> quizzesAudit = new ArrayList<>();
        resultList.forEach(quiz->{
            quizzesAudit.add(new Quiz(quiz.getId(), quiz.getName(), null, null, null, quiz.getDescription(), null, null, quiz.getIsActive()));
        });
        entitiesStatusUpdateAuditDTO.setDataList(quizzesAudit);
        auditLogService.saveAuditLog(EOperation.UPDATE, entitiesStatusUpdateAuditDTO);
        return new ResponseDTO<>(true, "Quizzes status updated successfully.", resultList);
    }

    @Override
    public QuizDTO getQuizDetailsById(Integer id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setIsActive(quiz.getIsActive());
            quizDTO.setId(quiz.getId());
            quizDTO.setName(quiz.getName());
            quizDTO.setCategoryName(quiz.getCategory().getName());
            quizDTO.setCategoryId(quiz.getCategory().getId());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setQuestionDTOList(quiz.getQuestions().stream().map(question -> new QuestionDTO(question.getId(),question.getName(), quiz.getId(), question.getOptionList().stream().map(option -> new OptionDTO(option.getId(), option.getName(), option.getIsCorrect())).collect(Collectors.toList()), question.getIsActive())).collect(Collectors.toList()));

            return quizDTO;

        }
        throw new IllegalArgumentException("Quiz Not Found.");
    }

    @Override
    public QuizIdsWithQuizCountDTO getQuizIdsWithQuizCount(Integer categoryId, String searchInput) {
        QuizIdsWithQuizCountProjection quizIdsWithQuizCountProjection = quizRepository.findQuizIdsWithQuizCount(searchInput, categoryId);
        return new QuizIdsWithQuizCountDTO(quizIdsWithQuizCountProjection.getActiveIds() != null ? Arrays.stream(quizIdsWithQuizCountProjection.getActiveIds().split(",")).map(Integer::parseInt).collect(Collectors.toList()) : new ArrayList<>(), quizIdsWithQuizCountProjection.getInActiveIds() != null ? Arrays.stream(quizIdsWithQuizCountProjection.getInActiveIds().split(",")).map(Integer::parseInt).collect(Collectors.toList()) : new ArrayList<>());
    }

    @Override
    public EntityUpdateTrailDTO<QuizDTO> getQuizUpdateTrailDTO(Integer entityId) {
        List<AuditLogDTO<String>> auditLogDTOList = auditLogService.getAuditLogByEntity("Quiz", Long.valueOf(entityId));
        EntityUpdateTrailDTO<QuizDTO> quizUpdateTrailDTO = new EntityUpdateTrailDTO<>();
        List<AuditLogDTO<QuizDTO>> quizAuditLogList = new ArrayList<>();
        auditLogDTOList.forEach(auditLogDTO->{
            Quiz quiz = jsonConverter.convertToObject(auditLogDTO.getData(), new TypeReference<Quiz>() {
            });
            QuizDTO quizDTO = new QuizDTO(quiz.getId(), quiz.getName(), null, quiz.getCategory().getName(), null, quiz.getDescription(), null, quiz.getAttemptableCount(), quiz.getDuration(), quiz.getIsActive());
            if(auditLogDTO.getActionType().equals(EOperation.CREATE)){
                quizUpdateTrailDTO.setActualEntity(quizDTO);
                quizUpdateTrailDTO.setCreatedBy(auditLogDTO.getUpdatedBy());
                quizUpdateTrailDTO.setCreatedAt(auditLogDTO.getDate());
            }else {
                quizAuditLogList.add(new AuditLogDTO<>(auditLogDTO.getId(), null, quizDTO, auditLogDTO.getUpdatedBy(), auditLogDTO.getDate()));
            }
        });
        quizUpdateTrailDTO.setAuditLogDTOList(quizAuditLogList);
        return quizUpdateTrailDTO;
    }

    @Override
    public List<EntitiesStatusUpdateTrailDTO<QuizDTO>> getQuizzesStatusUpdateTrailDTO() {
        List<AuditLogDTO<String>> auditLogDTOList = auditLogService.getAuditLogByEntity("Quiz", 0L);
        List<EntitiesStatusUpdateTrailDTO<QuizDTO>> entitiesStatusUpdateTrailDTOS = new ArrayList<>();
        auditLogDTOList.forEach(auditLog->{
            EntitiesStatusUpdateAuditDTO<Quiz> quizEntitiesStatusUpdateAuditDTO = jsonConverter.convertToObject(auditLog.getData(), new TypeReference<EntitiesStatusUpdateAuditDTO<Quiz>>() {
            });
            entitiesStatusUpdateTrailDTOS.add(
                    new EntitiesStatusUpdateTrailDTO<>(
                            auditLog.getUpdatedBy(),
                            auditLog.getDate(),
                            quizEntitiesStatusUpdateAuditDTO.getDataList().stream().map(quiz -> new QuizDTO(quiz.getId(), quiz.getName(), null, null, null, quiz.getDescription(), null, null, null, quiz.getIsActive())).collect(Collectors.toList()),
                            quizEntitiesStatusUpdateAuditDTO.getRemark(),
                            quizEntitiesStatusUpdateAuditDTO.getDataList().get(0).getIsActive()
                    )
            );
        });
        return entitiesStatusUpdateTrailDTOS;
    }

    @Override
    public QuizInstructionDTO getQuizInstructions(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new IllegalArgumentException("Quiz not found with quizId: "+quizId));
        return new QuizInstructionDTO(quiz.getName(), quiz.getCategory().getName(), quiz.getDescription(), quiz.getDuration(), quiz.getAttemptableCount(), Arrays.asList(
                String.format("You will get %s questions.", quiz.getAttemptableCount()),
                "Each question carries 1 marks.",
                "No negative marking.",
                "Do not refresh or close the tab during the quiz.",
                "The quiz will auto-submit when time runs out.",
                "Ensure a stable internet connection."
        ));
    }

    @Override
    public ResponseDTO<QuizStartResponseDTO> startQuizService(Integer quizId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isQuizInProgress = inMemoryQuizProgressStore.getQuizProgressForUser(username) != null;
        if(isQuizInProgress){
            return new ResponseDTO<>(false, "A Quiz is already in Progress. You can't start another before submitting it.", null);
        }
        Quiz quiz = quizRepository.findByIdAndIsActiveTrue(quizId).orElseThrow(()-> new IllegalArgumentException("Quiz not found with Quiz Id: "+quizId));
        QuizProgressDTO quizProgressDTO = new QuizProgressDTO(quiz.getId(), quiz.getName(), Instant.now(), Instant.now(), (long) (quiz.getDuration()*60),quiz.getQuestions().stream().filter(Question::getIsActive).map(question -> new QuizProgressQuestionDTO(question.getId(), question.getName(), question.getOptionList().stream().map(option-> new OptionDTO(option.getId(), option.getName(), false)).collect(Collectors.toList()), false, false)).collect(Collectors.toList()));
        if(quizProgressDTO.getQuizProgressQuestionDTOList().isEmpty()){
            return new ResponseDTO<>(false, "No Active Questions Found.", null);
        }
        inMemoryQuizProgressStore.addUserWithQuizToMap(username, quizProgressDTO);
        return new ResponseDTO<>(true, null, new QuizStartResponseDTO(inMemoryQuizProgressStore.getQuizProgressForUser(username).getId(), inMemoryQuizProgressStore.getQuizProgressForUser(username).getQuizProgressQuestionDTOList().get(0).getId()));
    }

    private boolean isPassed(Integer totalQuestions, Integer correctAnswers){
        return (double)correctAnswers / totalQuestions * 100 >= 33;
    }

    private boolean hasChanges(Quiz quiz, QuizDTO quizDTO){
        return ! (quiz.getDescription().trim().equals(quizDTO.getDescription().trim()) && quiz.getAttemptableCount().equals(quizDTO.getAttemptableCount()) && quiz.getDuration().equals(quizDTO.getDuration()));
    }
}
