/*
 * Copyright (C) 2014 Snowdream Mobile <yanghui1986527@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pxgd.hyena.com.nightbook.utils;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.nightbook.entity.Book;
import pxgd.hyena.com.nightbook.entity.BookItem;

/**
 * Created by hui.yang on 2014/12/7.
 */
public class BookManager {

    public static void getBooks(final String url, final CallBack callBack) {
        List<Book> list = null;
        if (TextUtils.isEmpty(url)) {
            callBack.callback(list);
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<Book> list = parseBooks(response);
                if (callBack != null) {
                    callBack.callback(list);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }




    /**
     * Parse Books
     *
     * @param obj
     * @return
     */
    private static List<Book> parseBooks(JSONObject obj) {
        List<Book> list = null;

        if (obj == null) {
            return list;
        }

        try {
            list = new ArrayList<>();
            JSONArray booksArray = obj.getJSONArray("books");
            int length = booksArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject oj = booksArray.getJSONObject(i);

                if (oj == null) {
                    continue;
                }

                Book book = new Book();

                List<BookItem> bookItemList = parseBook(oj);

                if (bookItemList != null){
                    book.setList(bookItemList);
                }

                list.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Parse Book
     *
     * @param obj
     * @return
     */
    private static List<BookItem> parseBook(JSONObject obj) {
        List<BookItem> list = null;

        if (obj == null) {
            return list;
        }

        try {
            list = new ArrayList<>();
            JSONArray array = obj.getJSONArray("book");
            int length = array.length();
            for (int i = 0; i < length; i++) {
                JSONObject oj = array.getJSONObject(i);

                if (oj == null) {
                    continue;
                }

                BookItem item = new BookItem();
                item.setLanguage(oj.optString("language"));
                item.setCountry(oj.optString("country"));
                item.setAuthor(oj.optString("author"));
                item.setTitle(oj.optString("title"));
                item.setDesc(oj.optString("desc"));
                item.setDefault(oj.optBoolean("default"));
                item.setUrl(oj.optString("url"));
                item.setImg(oj.optString("img"));

                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
