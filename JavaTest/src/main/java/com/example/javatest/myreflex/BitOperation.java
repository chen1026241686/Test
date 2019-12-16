package com.example.javatest.myreflex;

/**
 * @author ChenYasheng
 * @date 2019/12/13
 * @Description 位操作
 */
public class BitOperation {
    /**
     * 允许添加
     */
    public static final int ADD = 1 << 0;
    /**
     * 允许删除
     */
    public static final int DELETE = 1 << 1;
    /**
     * 允许修改
     */
    public static final int UPDATE = 1 << 2;
    /**
     * 允许查询
     */
    public static final int SELECT = 1 << 3;

    private int permission;

    public BitOperation(int permission) {
        this.permission = permission;
    }

    /**
     * 添加某种权限
     *
     * @param addPermission
     */
    public void add(int addPermission) {
        permission |= addPermission;
    }

    /**
     * 删除某个权限
     *
     * @param deletePermission
     */
    public void delete(int deletePermission) {
        permission = permission & (~deletePermission);
    }

    /**
     * 是否拥有某个权限
     *
     * @param hasPermission
     * @return
     */
    public boolean hasPermission(int hasPermission) {
        return (permission & hasPermission) == hasPermission;
    }


    public static void main(String[] args) {
        BitOperation bitOperation = new BitOperation(0);

        bitOperation.add(BitOperation.ADD);
        bitOperation.add(BitOperation.DELETE);
        System.out.println(bitOperation.hasPermission(BitOperation.ADD));
        bitOperation.delete(BitOperation.ADD);
        System.out.println(bitOperation.hasPermission(BitOperation.ADD));

    }

}
