package com.zhy.submit.teacher.utils;

import com.zhy.submit.teacher.VO.ResultVO;
import com.zhy.submit.teacher.enums.ResultCode;
import com.zhy.submit.teacher.enums.ResultEnum;

public class ResultVOUtils {
    //失败时调用
    public static ResultVO error(ResultCode resultCode){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(resultCode.code());
        resultVO.setMsg(resultCode.message());
        return resultVO;
    }

    //成功时调用
    public static ResultVO success(int count,Object data){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setCount(count);
        resultVO.setData(data);
        return resultVO;

    }
}
