package ua.dp.michaellang.weather.domain.interactor;

import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ua.dp.michaellang.weather.domain.usecase.UseCase;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseInteractor implements UseCase {

    private final CompositeDisposable disposables;

    BaseInteractor() {
        this.disposables = new CompositeDisposable();
    }

/*    public void execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }*/

    @Override
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
