package com.concursos.angelica.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/concursos/angelica/ui/viewmodel/PerformanceViewModel;", "Landroidx/lifecycle/ViewModel;", "userProgressDao", "Lcom/concursos/angelica/data/dao/UserProgressDao;", "(Lcom/concursos/angelica/data/dao/UserProgressDao;)V", "_tempoMedio", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "statsPorDisciplina", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/concursos/angelica/data/dao/DisciplinaStat;", "getStatsPorDisciplina", "()Lkotlinx/coroutines/flow/StateFlow;", "tempoMedio", "getTempoMedio", "totalAcertos", "", "getTotalAcertos", "totalRespondidas", "getTotalRespondidas", "app_debug"})
public final class PerformanceViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.UserProgressDao userProgressDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> totalRespondidas = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> totalAcertos = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.concursos.angelica.data.dao.DisciplinaStat>> statsPorDisciplina = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Double> _tempoMedio = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> tempoMedio = null;
    
    public PerformanceViewModel(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.UserProgressDao userProgressDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotalRespondidas() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotalAcertos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.concursos.angelica.data.dao.DisciplinaStat>> getStatsPorDisciplina() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getTempoMedio() {
        return null;
    }
}