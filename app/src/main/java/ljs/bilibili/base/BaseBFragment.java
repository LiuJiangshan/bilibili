package ljs.bilibili.base;

public abstract class BaseBFragment extends ljs.android.fragment.BaseFragment {
    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public BaseBActivity getBActivity() {
        return (BaseBActivity) getActivity();
    }
}
