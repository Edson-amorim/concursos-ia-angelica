package com.concursos.angelica.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010 \u001a\u00020!J(\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00122\b\b\u0002\u0010&\u001a\u00020\tJ\u0006\u0010\'\u001a\u00020!J\u0006\u0010(\u001a\u00020!J\u0006\u0010)\u001a\u00020!J&\u0010*\u001a\u00020!2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\r2\u0006\u0010.\u001a\u00020\tJ\u000e\u0010/\u001a\u00020!2\u0006\u00100\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016\u00a8\u00061"}, d2 = {"Lcom/concursos/angelica/ui/viewmodel/QuizViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/concursos/angelica/data/repository/QuestionRepository;", "(Lcom/concursos/angelica/data/repository/QuestionRepository;)V", "_currentFilters", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/concursos/angelica/data/model/FilterCriteria;", "_currentIndex", "", "_filterOptions", "Lcom/concursos/angelica/data/repository/FilterOptions;", "_isGeneratingQuestions", "", "_questions", "", "Lcom/concursos/angelica/data/model/Question;", "_updateMessage", "", "currentFilters", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentFilters", "()Lkotlinx/coroutines/flow/StateFlow;", "currentIndex", "getCurrentIndex", "filterOptions", "getFilterOptions", "isGeneratingQuestions", "questions", "getQuestions", "updateMessage", "getUpdateMessage", "clearUpdateMessage", "", "generateQuestionsWithAi", "banca", "disciplina", "assunto", "quantidade", "loadQuestions", "nextQuestion", "previousQuestion", "submitAnswer", "questaoId", "resposta", "acertou", "tempoSegundos", "updateFilters", "newFilters", "app_debug"})
public final class QuizViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.repository.QuestionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.concursos.angelica.data.model.FilterCriteria> _currentFilters = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.concursos.angelica.data.model.FilterCriteria> currentFilters = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.concursos.angelica.data.model.Question>> _questions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.concursos.angelica.data.model.Question>> questions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _currentIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> currentIndex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.concursos.angelica.data.repository.FilterOptions> _filterOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.concursos.angelica.data.repository.FilterOptions> filterOptions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isGeneratingQuestions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGeneratingQuestions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _updateMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> updateMessage = null;
    
    public QuizViewModel(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.repository.QuestionRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.concursos.angelica.data.model.FilterCriteria> getCurrentFilters() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.concursos.angelica.data.model.Question>> getQuestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCurrentIndex() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.concursos.angelica.data.repository.FilterOptions> getFilterOptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGeneratingQuestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getUpdateMessage() {
        return null;
    }
    
    public final void clearUpdateMessage() {
    }
    
    public final void generateQuestionsWithAi(@org.jetbrains.annotations.NotNull()
    java.lang.String banca, @org.jetbrains.annotations.NotNull()
    java.lang.String disciplina, @org.jetbrains.annotations.NotNull()
    java.lang.String assunto, int quantidade) {
    }
    
    public final void updateFilters(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.FilterCriteria newFilters) {
    }
    
    public final void loadQuestions() {
    }
    
    public final void submitAnswer(int questaoId, @org.jetbrains.annotations.NotNull()
    java.lang.String resposta, boolean acertou, int tempoSegundos) {
    }
    
    public final void nextQuestion() {
    }
    
    public final void previousQuestion() {
    }
}