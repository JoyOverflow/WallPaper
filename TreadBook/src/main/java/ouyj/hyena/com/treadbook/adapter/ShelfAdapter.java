package ouyj.hyena.com.treadbook.adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ouyj.hyena.com.treadbook.Config;
import ouyj.hyena.com.treadbook.R;
import ouyj.hyena.com.treadbook.db.BookList;
import ouyj.hyena.com.treadbook.view.DragGridListener;
import ouyj.hyena.com.treadbook.view.DragGridView;
/**
 * 自定义适配器类
 */
public class ShelfAdapter extends BaseAdapter implements DragGridListener {

    //上下文对象和数据源
    private Context contex;
    private List<BookList> list;

    private LayoutInflater inflater;


    private int mHidePosition = -1;
    private Typeface typeface;
    protected List<AsyncTask<Void, Void, Boolean>> myAsyncTasks = new ArrayList<>();
    private int[] firstLocation;
    private Config config;

    /**
     * 构造方法
     * @param context
     * @param list
     */
    public ShelfAdapter(Context context, List<BookList> list){
        this.contex = context;
        this.list = list;

        config = Config.getInstance();
        //typeface = config.getTypeface();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * 数据源的记录数
     * @return
     */
    @Override
    public int getCount() {
        if(list.size() < 10)
            return 10;
        else
            return list.size();
    }
    /**
     * 返回指定项
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 每一项的视图
     * @param position
     * @param contentView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (contentView == null) {

            contentView = inflater.inflate(R.layout.shelfitem, null);
            viewHolder = new ViewHolder(contentView);

            viewHolder.name.setTypeface(typeface);

            //将查找到的视图缓存起来方便重用
            contentView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) contentView.getTag();

        if(list.size() > position){

            if(position == mHidePosition)
                contentView.setVisibility(View.INVISIBLE);
            else
                contentView.setVisibility(View.VISIBLE);

            //是否显示删除按钮
            if (DragGridView.getShowDeleteButton())
                viewHolder.deleteItem_IB.setVisibility(View.VISIBLE);
            else
                viewHolder.deleteItem_IB.setVisibility(View.INVISIBLE);

            //设置项内视图的文本
            String bookName = list.get(position).getBookname();
            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.name.setText(bookName);
        }
        else {
            contentView.setVisibility(View.INVISIBLE);
        }
        return contentView;
    }
    static class ViewHolder {
        @Bind(R.id.ib_close)
        ImageButton deleteItem_IB;
        @Bind(R.id.tv_name)
        TextView name;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




    /**
     * Drag移动时item交换数据,并在数据库中更新交换后的位置数据
     * @param oldPosition
     * @param newPosition
     */
    @Override
    public void reorderItems(int oldPosition, int newPosition) {

        BookList temp = list.get(oldPosition);
        List<BookList> bookLists1 = new ArrayList<>();
        bookLists1 = DataSupport.findAll(BookList.class);

        int tempId = bookLists1.get(newPosition).getId();
       // Log.d("oldposotion is",oldPosition+"");
       // Log.d("newposotion is", newPosition + "");
        if(oldPosition < newPosition){
            for(int i=oldPosition; i<newPosition; i++){
                //获得交换前的ID,必须是数据库的真正的ID，如果使用bilist获取id是错误的，因为bilist交换后id是跟着交换的
                List<BookList> bookLists = new ArrayList<>();
                bookLists = DataSupport.findAll(BookList.class);
                int dataBasesId = bookLists.get(i).getId();
                Collections.swap(list, i, i + 1);

                updateBookPosition(i,dataBasesId, list);

            }
        }else if(oldPosition > newPosition){
            for(int i=oldPosition; i>newPosition; i--) {
                List<BookList> bookLists = new ArrayList<>();
                bookLists = DataSupport.findAll(BookList.class);
                int dataBasesId = bookLists.get(i).getId();

                Collections.swap(list, i, i - 1);

                updateBookPosition(i,dataBasesId,list);

            }
        }

        list.set(newPosition, temp);
        updateBookPosition(newPosition, tempId, list);

    }

    /**
     * 两个item数据交换结束后，把不需要再交换的item更新到数据库中
     * @param position
     * @param bookLists
     */
    public void updateBookPosition (int position,int databaseId,List<BookList> bookLists) {
        BookList bookList = new BookList();
        String bookpath = bookLists.get(position).getBookpath();
        String bookname = bookLists.get(position).getBookname();
        bookList.setBookpath(bookpath);
        bookList.setBookname(bookname);
        bookList.setBegin(bookLists.get(position).getBegin());
        bookList.setCharset(bookLists.get(position).getCharset());
        //开线程保存改动的数据到数据库
        //使用litepal数据库框架update时每次只能update一个id中的一条信息，如果相同则不更新。
        upDateBookToSqlite3(databaseId , bookList);
    }

    /**
     * 隐藏指定项
     * @param hidePosition
     */
    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    /**
     * 删除书本
     * @param deletePosition
     */
    @Override
    public void removeItem(int deletePosition) {

        String bookpath = list.get(deletePosition).getBookpath();
        DataSupport.deleteAll(BookList.class, "bookpath = ?", bookpath);
        list.remove(deletePosition);
       // Log.d("删除的书本是", bookpath);

        notifyDataSetChanged();

    }

    public void setBookList(List<BookList> bookLists){
        this.list = bookLists;
        notifyDataSetChanged();
    }
    /**
     * Book打开后位置移动到第一位
     * @param openPosition
     */
    @Override
    public void setItemToFirst(int openPosition) {

        List<BookList> bookLists1 = new ArrayList<>();
        bookLists1 = DataSupport.findAll(BookList.class);
        int tempId = bookLists1.get(0).getId();
        BookList temp = bookLists1.get(openPosition);
       // Log.d("setitem adapter ",""+openPosition);
        if(openPosition!=0) {
            for (int i = openPosition; i > 0 ; i--) {
                List<BookList> bookListsList = new ArrayList<>();
                bookListsList = DataSupport.findAll(BookList.class);
                int dataBasesId = bookListsList.get(i).getId();

               Collections.swap(bookLists1, i, i - 1);
               updateBookPosition(i, dataBasesId, bookLists1);
            }

            bookLists1.set(0, temp);
            updateBookPosition(0, tempId, bookLists1);
            for (int j = 0 ;j<bookLists1.size();j++) {
                String bookpath = bookLists1.get(j).getBookpath();
              //  Log.d("移动到第一位",bookpath);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void nitifyDataRefresh() {
        notifyDataSetChanged();
    }

    public void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        myAsyncTasks.add(asyncTask.execute());
    }

    /**
     * 数据库书本信息更新
     * @param databaseId  要更新的数据库的书本ID
     * @param bookList
     */
    public void upDateBookToSqlite3(final int databaseId,final BookList bookList) {

        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    bookList.update(databaseId);
                } catch (DataSupportException e) {
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {

                } else {
                    Log.d("保存到数据库结果-->", "失败");
                }
            }
        });
    }

}
