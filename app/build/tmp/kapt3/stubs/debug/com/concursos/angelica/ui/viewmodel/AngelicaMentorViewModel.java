package com.concursos.angelica.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tJ\u0010\u0010\u0016\u001a\u00020\u00102\b\u0010\u0015\u001a\u0004\u0018\u00010\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/concursos/angelica/ui/viewmodel/AngelicaMentorViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/concursos/angelica/data/repository/AngelicaMentorRepository;", "(Lcom/concursos/angelica/data/repository/AngelicaMentorRepository;)V", "_isLoading", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_mentorAdvice", "", "isLoading", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "mentorAdvice", "getMentorAdvice", "explainQuestion", "", "enunciado", "respostaEscolhida", "correta", "justificativa", "banca", "fetchDiagnosis", "app_debug"})
public final class AngelicaMentorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.repository.AngelicaMentorRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _mentorAdvice = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> mentorAdvice = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    
    public AngelicaMentorViewModel(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.repository.AngelicaMentorRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getMentorAdvice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    public final void fetchDiagnosis(@org.jetbrains.annotations.Nullable()
    java.lang.String banca) {
    }
    
    public final void explainQuestion(@org.jetbrains.annotations.NotNull()
    java.lang.String enunciado, @org.jetbrains.annotations.NotNull()
    java.lang.String respostaEscolhida, @org.jetbrains.annotations.NotNull()
    java.lang.String correta, @org.jetbrains.annotations.NotNull()
    java.lang.String justificativa, @org.jetbrains.annotations.NotNull()
    java.lang.String banca) {
    }
}