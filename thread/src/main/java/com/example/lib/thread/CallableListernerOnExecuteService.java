package com.example.lib.thread;

public class CallableListernerOnExecuteService<T> implements AsyncExecueService.CallableListener<T> {
    private AsyncExecueService.CallableListener<T> callableListener;
    private ExecuteService executeService;

    public CallableListernerOnExecuteService(AsyncExecueService.CallableListener<T> callableListener,
                                             ExecuteService executeService) {
        this.callableListener = callableListener;
        this.executeService = executeService;
    }

    public static <T> AsyncExecueService.CallableListener<T> decorate(
            AsyncExecueService.CallableListener<T> callableListener, ExecuteService executeService) {
        if (callableListener != null && executeService != null) {
            return new CallableListernerOnExecuteService<>(callableListener, executeService);
        }
        return callableListener;

    }

    @Override
    public void onFinish(final T t) {
        executeService.execute(new Runnable() {
            @Override
            public void run() {
                callableListener.onFinish(t);
            }
        });
    }
}
