介绍：万能适配器和下拉刷新下拉加载更多结合

使用方法：
###一、布局文件
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <com.aspsine.irecyclerview.IRecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/iRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
                <!--可以自定义上拉和下拉头，有默认-->
                <!--app:refreshHeaderLayout="@layout/layout_irecyclerview_classic_refresh_header"-->
                <!--app:loadMoreFooterLayout="@layout/layout_irecyclerview_load_more_footer"-->
        app:loadMoreEnabled="true"
        app:refreshEnabled="true"
        tools:context=".ui.activity.MainActivity" />
    <RelativeLayout
        android:id="@+id/rl_root"
        android:layout_width="match_parent"
        android:visibility="gone"
        android:layout_height="match_parent">
        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="match_parent"
            android:layout_centerInParent="true"
            android:layout_height="80dp"
            android:text="数据为空哦"
            android:gravity="center"
            android:padding="10dp"
            android:textColor="@android:color/black"
            android:textSize="18sp" />

    </RelativeLayout>
    </RelativeLayout>

###二、java使用

######第一种情况：itemtype为一种

public class TestActivity extends FragmentActivity implements OnRefreshListener, OnLoadMoreListener {

    private IRecyclerView iRecyclerView;
    private CommonRecycleViewAdapter<String> adapter;
    private RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iRecyclerView = (IRecyclerView) findViewById(R.id.iRecyclerView);
        rl_root= (RelativeLayout) findViewById(R.id.rl_root);
        iRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CommonRecycleViewAdapter<String>(this, R.layout.test_item)
        {
            @Override
            public void convert(final ViewHolderHelper holder, final String s)
            {
                holder.setOnClickListener(R.id.tvTitle, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.remove(s);
                        Log.d("position",holder.getAdapterPosition()+"");
                        Log.d("positioncount",adapter.getItemCount()+"");
                    }
                });
            }
        };
        //adapter.openLoadAnimation(new ScaleInAnimation());
        iRecyclerView.setIAdapter(adapter);
        iRecyclerView.setOnRefreshListener(this);
        iRecyclerView.setOnLoadMoreListener(this);
        iRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRefresh() {
        iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        refresh();
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (iRecyclerView.canLoadMore() && adapter.getItemCount() > 0) {
            iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
            loadMore();
        }
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(false);

                // we can set view
                ArrayList<String> strings=new ArrayList<>();
                for(int i=0;i<1;i++){
                    strings.add(i+"个鸭子");
                }
                adapter.replaceAll(strings);

            }
        },500);

    }

    private void loadMore() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> strings=new ArrayList<>();
                for(int i=adapter.getSize();i<adapter.getSize()+10;i++){
                    strings.add(i+"个鸭子");
                }
                adapter.addAll(strings);
                iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
            }
        },500);

    }
}

######第二种情况：itemtype为多种

    public class TestActivity extends FragmentActivity implements OnRefreshListener, OnLoadMoreListener {

        private IRecyclerView iRecyclerView;
        private ChatAdapter adapter;
        private ArrayList<String>strings=new ArrayList<>();
        private RelativeLayout rl_root;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            iRecyclerView = (IRecyclerView) findViewById(R.id.iRecyclerView);
            rl_root= (RelativeLayout) findViewById(R.id.rl_root);
            iRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter=new ChatAdapter(this, strings);
            //设置动画，默认渐变
            adapter.openLoadAnimation(new ScaleInAnimation());
            iRecyclerView.setIAdapter(adapter);
            iRecyclerView.setOnRefreshListener(this);
            iRecyclerView.setOnLoadMoreListener(this);
            iRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    iRecyclerView.setRefreshing(true);
                }
            });
        }

        @Override
        public void onRefresh() {
            iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
            refresh();
        }

        @Override
        public void onLoadMore(View loadMoreView) {
            if (iRecyclerView.canLoadMore() && adapter.getItemCount() > 0) {
                iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
                loadMore();
            }
        }

        private void refresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    iRecyclerView.setRefreshing(false);

                    // we can set view
                    ArrayList<String> strings=new ArrayList<>();
                    for(int i=0;i<18;i++){
                        strings.add(i+"个鸭子");
                    }
                    adapter.replaceAll(strings);

                }
            },500);

        }

        private void loadMore() {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> strings=new ArrayList<>();
                    for(int i=adapter.getSize();i<adapter.getSize()+10;i++){
                        strings.add(i+"个鸭子");
                    }
                    adapter.addAll(strings);
                    iRecyclerView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                }
            },500);

        }
    }
    /**
    *
    *多种类型适配器
    */
    public class ChatAdapter extends MultiItemCommonAdapter<String>
    {
        public ChatAdapter(Context context, List<String> datas)
        {
            super(context, datas, new MultiItemTypeSupport<String>()
            {

                @Override
                public int getLayoutId(int position) {
                    if (position%2==0)
                    {
                        return R.layout.test_item;
                    }
                    return R.layout.test_item2;
                }

                @Override
                public int getItemViewType(int position, String msg)
                {
                    if (position%2==0)
                    {
                        return R.layout.test_item;
                    }
                    return R.layout.test_item2;
                }
            });
        }

        @Override
        public void convert(ViewHolderHelper holder, String s) {
            switch (holder.getLayoutId())
            {
                case R.layout.test_item:
                    holder.setText(R.id.tvTitle,s);
                    break;
                case R.layout.test_item2:
                    holder.setText(R.id.tvTitle,s);
                    break;
            }
        }
    }


