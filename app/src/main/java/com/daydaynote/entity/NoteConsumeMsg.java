package com.daydaynote.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${王sir} on 2017/10/11.
 * application
 * 记录消费内容的实体类
 */

/*@Entity：告诉GreenDao该对象为实体，
        只有被@Entity注释的Bean类才能被dao类操作
@Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)
        表示主键会自增，如果false就会使用旧值
@Property：可以自定义字段名，注意外键不能使用该属性
@NotNull：属性不能为空
@Transient：使用该注释的属性不会被存入数据库的字段中
@Unique：该属性值必须在数据库中是唯一值
@Generated：编译后自动生成的构造函数、方法等的注释，
        提示构造函数、方法等不能被修改*/
@Entity
public class NoteConsumeMsg {

    private Long id;//这个地方一定要注意，是Long类型，不是long类型
    @Unique
    private String noteTime;//记录时间
    @Property
    private String type;//消费类型
    @Property
    private Integer typeIcon;//消费类型对应图标
    @Property
    private String consumeSum;//消费金额
    @Property
    private String consumeDate;//消费日期
    @Property
    private String noteInfo;//备注内容
    @Property
    private String notePicPah;//备注图片



@Generated(hash = 602722756)
public NoteConsumeMsg(Long id, String noteTime, String type, Integer typeIcon,
        String consumeSum, String consumeDate, String noteInfo,
        String notePicPah) {
    this.id = id;
    this.noteTime = noteTime;
    this.type = type;
    this.typeIcon = typeIcon;
    this.consumeSum = consumeSum;
    this.consumeDate = consumeDate;
    this.noteInfo = noteInfo;
    this.notePicPah = notePicPah;
}
@Generated(hash = 739820676)
public NoteConsumeMsg() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getNoteTime() {
    return this.noteTime;
}
public void setNoteTime(String noteTime) {
    this.noteTime = noteTime;
}
public String getType() {
    return this.type;
}
public void setType(String type) {
    this.type = type;
}
public Integer getTypeIcon() {
    return this.typeIcon;
}
public void setTypeIcon(Integer typeIcon) {
    this.typeIcon = typeIcon;
}
public String getConsumeSum() {
    return this.consumeSum;
}
public void setConsumeSum(String consumeSum) {
    this.consumeSum = consumeSum;
}
public String getConsumeDate() {
    return this.consumeDate;
}
public void setConsumeDate(String consumeDate) {
    this.consumeDate = consumeDate;
}
public String getNoteInfo() {
    return this.noteInfo;
}
public void setNoteInfo(String noteInfo) {
    this.noteInfo = noteInfo;
}
public String getNotePicPah() {
    return this.notePicPah;
}
public void setNotePicPah(String notePicPah) {
    this.notePicPah = notePicPah;
}
}