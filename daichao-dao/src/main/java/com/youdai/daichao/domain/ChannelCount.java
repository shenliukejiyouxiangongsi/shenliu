package com.youdai.daichao.domain;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Data
public class ChannelCount {

	    private int id;

	    private Integer channelId;

	    private String channelName;

	    private Integer loadPageNum;

	    private Integer viewPageNum;


	    private Integer registerNum;


	    private Double discountNum;


	    private Integer loginNum;


	    private String createTime;

	    private Integer everyViewPageNum;

}
