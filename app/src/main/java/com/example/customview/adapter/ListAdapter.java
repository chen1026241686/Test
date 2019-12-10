package com.example.customview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T, VH extends CustomViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Context context;
    private List<T> data;
    private final int itemLayout;

    private final LayoutInflater layoutInflater;

    public ListAdapter(Context context, int itemLayout) {
        this.context = context;
        this.itemLayout = itemLayout;
        data = new ArrayList<>();
        layoutInflater = LayoutInflater.from(this.context);
    }

    /**
     * 重新设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        clearData();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        if (data == null && data.size() < 1) {
            return;
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        if (this.data != null && data.size() > 0) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createBaseViewHolder(layoutInflater.inflate(itemLayout, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder, data.get(position));
    }

    protected abstract void onBindViewHolder(VH holder, T item);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    protected VH createBaseViewHolder(View view) {
        //当前类的字节码
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericVHClass(temp);
            temp = temp.getSuperclass();
        }
        VH k;
        if (z == null) {
            k = (VH) new CustomViewHolder(view);
        } else {
            k = createGenericVHInstance(z, view);
        }
        return k != null ? k : (VH) new CustomViewHolder(view);
    }

    private Class getInstancedGenericVHClass(Class z) {
        //泛型父亲。这里获得的是ListAdapter<T, VH extends CustomViewHolder>
        Type type = z.getGenericSuperclass();
        //如果是参数泛型 List<E>这种，
        if (type instanceof ParameterizedType) {
            //泛型V,VH entends CustomViewHolder
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                //
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (CustomViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && CustomViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }

    private VH createGenericVHInstance(Class z, View view) {
        try {
            //其中X是调用getClass方法对象的类
            Constructor constructor;
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
