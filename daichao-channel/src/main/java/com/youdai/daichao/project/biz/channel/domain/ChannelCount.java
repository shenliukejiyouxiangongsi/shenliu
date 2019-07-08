package com.youdai.daichao.project.biz.channel.domain;



import com.youdai.daichao.framework.aspectj.lang.annotation.Excel;
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

	    @Excel(name="渠道名称")
	    private String channelName;

		@Excel(name="app壳")
		private String shellName;

	    @Excel(name="落地页pv")
	    private Integer loadPageNum;

	    @Excel(name="落地页uv")
	    private Integer viewPageNum;

		@Excel(name="扣量落地页uv")
		private Integer disViewPageNum;

	    @Excel(name="实际注册数")
	    private Integer registerNum;


	    @Excel(name="扣量注册数")
	    private Double discountNum;


	    @Excel(name="登录数")
	    private Integer loginNum;


	    @Excel(name="注册时间")
	    private String createTime;

}
