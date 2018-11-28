package com.longfor.longjian.datathrough.app.appService.master.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longfor.longjian.datathrough.app.appService.master.MasterService;
import com.longfor.longjian.datathrough.app.util.DateUtil;
import com.longfor.longjian.datathrough.consts.OperationEnum;
import com.longfor.longjian.datathrough.domain.innerService.*;
import com.longfor.longjian.datathrough.po.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.common.util.Base64Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2018/11/17.
 */
@Service
@Slf4j
public class MasterServiceImpl implements MasterService{

    @Value("${master.msg.mdmSuccess}")
    private String mdmSuccess;

    @Value("${master.msg.mdmError}")
    private String mdmError;

    @Resource
    private RelLhCompanyToCompanyService relLhCompanyToCompanyService;

    @Resource
    private AdptProjService adptProjService;

    @Resource
    private MirrorPhaseCOneService mirrorPhaseCOneService;

    @Resource
    private MirrorPhaseCTwoService mirrorPhaseCTwoService;

    @Resource
    private MirrorPhaseCThreeService mirrorPhaseCThreeService;

    @Resource
    private MirrorPhaseCFourService mirrorPhaseCFourService;

    @Resource
    private AdptPhaseService adptPhaseService;

    @Resource
    private AdptGroupService adptGroupService;


    /**
     * 同步项目
     * @param jsonObject
     */
    @Override
    public void excuteProject(JSONObject jsonObject) {

        JSONObject result = new JSONObject();
        JSONArray itemarry = jsonObject.getJSONArray("ItemArray"); //操作的数据集合
        JSONArray errorArray = new JSONArray();
        Map<String,Object> map=relLhCompanyToCompanyService.getRelLhCompanyToCompanyMap();
        boolean hasError = false;
        for (int i = 0; i<itemarry.size(); i++) {
            JSONObject resultJson = new JSONObject();
            try {

                // 去掉地块信息，减少data字符串长度，保存到数据库
                JSONObject jsonnode = itemarry.getJSONObject(i);
                JSONObject projResult = operProject(jsonnode,map);
                if("500".equals(projResult.getString("code"))) {
                    resultJson.put("STATUS", "E");
                    hasError = true;
                } else {
                    resultJson.put("STATUS", "S");
                }
                resultJson.put("TYPE", "PRJ");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PR_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", projResult.getString("message"));
                errorArray.add(resultJson);
            } catch(Exception e) {
                resultJson.put("TYPE", "PRJ");
                resultJson.put("STATUS", "E");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PR_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", e.getMessage());
                errorArray.add(resultJson);
                hasError = true;
            }
        }
        result.put("ItemArray", errorArray);
        executorCallBack(result.toString(),hasError);

    }

    /**
     * 同步分期
     * @param jsonObject
     */
    @Override
    public void excuteStage(JSONObject jsonObject) {

        JSONArray itemarry = jsonObject.getJSONArray("ItemArray");
        JSONObject result = new JSONObject();
        result.put("SystemID", jsonObject.getString("SystemID"));
        JSONArray errorArray = new JSONArray();
        boolean hasError = false;
        for (int i = 0; i<itemarry.size(); i++) {
            JSONObject resultJson = new JSONObject();
            resultJson.put("TYPE", "P");
            JSONObject jsonnode = itemarry.getJSONObject(i);
            try {
                JSONObject opResult = operStage(jsonnode);
                if("500".equals(opResult.getString("code"))) {
                    resultJson.put("STATUS", "E");
                    hasError = true;
                } else {
                    resultJson.put("STATUS", "S");
                }
                resultJson.put("ID", itemarry.getJSONObject(i).get("PH_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", opResult.getString("message"));
                errorArray.add(resultJson);
            } catch( Exception e ) {
                resultJson.put("STATUS", "E");
                resultJson.put("ID", itemarry.getJSONObject(i).getString("PH_ID"));
                resultJson.put("VERSION", itemarry.getJSONObject(i).get("SAP_VER"));
                resultJson.put("LOG", e.getMessage());
                errorArray.add(resultJson);
                hasError = true;
            }
        }
        result.put("ItemArray", errorArray);
        executorCallBack(result.toString(),hasError);


    }

    /**
     * 项目操作
     * */
    @Transactional
    private JSONObject operProject(JSONObject project,Map<String,Object>map) {
        JSONObject result = new JSONObject();
        String operCode = project.getString("OP_CODE").trim();//操作码
        String projName = project.getString("PR_NAME").trim();//项目名称
        String projCode=project.getString("HIS_CODE").trim();//项目历史code
        String gsCode=project.getString("PR_REGID").trim();//公司code
        AdptProj adptProj=adptProjService.getByPrCode(projCode);
        int num=-1;
        if("C".equals(operCode)) { // 新增
            if (adptProj != null ) { // 项目已经存在
                num=updateAptProject(adptProj,projName,gsCode);
                result=updateResultStatus(num,projName,result);
            }else {
                AdptProj newAdptProj=new AdptProj();
                newAdptProj.setLhXmcode(projCode);
                newAdptProj.setLhXmname(projName);
                newAdptProj.setLhGscode(gsCode);
                newAdptProj.setCompanyId(Integer.parseInt(String.valueOf(map.get(gsCode))));
                newAdptProj.setGroupId(4);
                newAdptProj.setMenuType("1");
                newAdptProj.setCreateAt(new Date());
                newAdptProj.setUpdateAt(new Date());
                num= adptProjService.createAdptProj(newAdptProj);
                result=insertResultStatus(num,projName,result);
            }
        } else if("U".equals(operCode)) { // 更新
            num=updateAptProject(adptProj,projName,gsCode);
            result=updateResultStatus(num,projName,result);

        } else if("D".equals(operCode)) { // 删除
            if (adptProj != null ) {
                adptProj.setDeleteAt(new Date());
                num=adptProjService.updateAdptProj(adptProj);
            }
            result=deleteResultStatus(num,projName,result);
        }
        return result;
    }

    /**
     * 更新项目数据
     * @param adptProj
     * @param projName
     * @param gsCode
     */
    private int  updateAptProject(AdptProj adptProj,String projName,String gsCode){
            adptProj.setLhXmname(projName);
            adptProj.setLhGscode(gsCode);
            adptProj.setUpdateAt(new Date());

           int num= adptProjService.updateAdptProj(adptProj);

           return num;
    }

    /**
     *按航道 处理分期数据
     * */
    @Transactional
    private JSONObject operStage(JSONObject dujson) throws ParseException {

        log.debug("data====="+JSON.toJSONString(dujson));

        String operCode = dujson.getString("OP_CODE").trim(); //C是新增  U是修改 D是删除
        String buId = dujson.getString("BU_ID").trim();//航道
        String phId = dujson.getString("PH_ID").trim();//分期身份证
        String sapVer = dujson.getString("SAP_VER").trim();//sap版本
        String stageName = dujson.getString("TREE_PHM");//分期名称
        String prId = dujson.getString("PR_ID").trim();//C4 项目身份证
        JSONObject result = new JSONObject();

        Map<String,Object> map=relLhCompanyToCompanyService.getRelLhCompanyToCompanyMap();
        int num = -1;
        if ("C1".equals(buId)) {//如果航道是C1
            MirrorPhaseCOne oldMirrorPhaseCOne = mirrorPhaseCOneService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过

            if ("C".equals(operCode)) { // 新增
                if (oldMirrorPhaseCOne != null) {
                    num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCOne.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            } else if ("U".equals(operCode)) {//修改
                num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCOne.getId(),map);//更新操作
                result = updateResultStatus(num, stageName, result);
            } else if ("D".equals(operCode)) {//删除
                num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCOne.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }

        }else if("C2".equals(buId)) {//如果航道是C2
            MirrorPhaseCTwo oldMirrorPhaseCTwo = mirrorPhaseCTwoService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)) { // 新增
                if (oldMirrorPhaseCTwo != null) {
                    num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCTwo.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            } else if ("U".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCTwo.getId(),map);//更新操作
                result = updateResultStatus(num, stageName, result);
            } else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCTwo.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("C3".equals(buId)) {//如果航道是C3
            MirrorPhaseCThree oldMirrorPhaseCThree = mirrorPhaseCThreeService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)) { // 新增
                if (oldMirrorPhaseCThree != null) {
                    num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCThree.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            } else if ("U".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCThree.getId(),map);//更新操作
                result = updateResultStatus(num, stageName, result);
            } else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCThree.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("C4".equals(buId)) {//如果航道是C4
            MirrorPhaseCFour oldMirrorPhaseCFour = mirrorPhaseCFourService.findByPhIdSapVer(prId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)) { // 新增
                if (oldMirrorPhaseCFour != null) {
                    num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCFour.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            } else if ("U".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCFour.getId(),map);//更新操作
                result = updateResultStatus(num, stageName, result);
            } else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCFour.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }

        JSONArray groups = dujson.getJSONArray("GroupArray");//组团

        // 处理分期下组团
        if(groups != null &&groups.size()>0 ) {
            num=saveOrUpdateAdptGroup(groups,phId);
            result=saveOrUpdateGroupResultStatus(num,result);
        }
        return result;
    }

    /**
     * 操作C1数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCOne(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseCOne mirrorPhaseCOne=new MirrorPhaseCOne();
        mirrorPhaseCOne.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCOne.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCOne.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCOne.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCOne.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCOne.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCOne.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCOne.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseCOne.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCOne.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseCOne.setPhCname(dujson.getString("PH_CNAME"));
        mirrorPhaseCOne.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseCOne.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseCOne.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCOne.setPhPrdlev(dujson.getString("PH_PRDLEV"));
        mirrorPhaseCOne.setPhDevlev(dujson.getString("PH_DEVLEV"));
        mirrorPhaseCOne.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseCOne.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));
        mirrorPhaseCOne.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseCOne.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseCOne.setPhOptyp(dujson.getString("PH_OPTYP"));
        mirrorPhaseCOne.setPhEquiR(dujson.getString("PH_EQUI_R"));
        mirrorPhaseCOne.setPhEquiX(dujson.getString("PH_EQUI_X"));
        mirrorPhaseCOne.setPhEquiT(dujson.getString("PH_EQUI_T"));
        mirrorPhaseCOne.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseCOne.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseCOne.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseCOne.setPrBugets(dujson.getString("PR_BUGETS"));
        mirrorPhaseCOne.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseCOne.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseCOne.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseCOne.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseCOne.setPhDelive(DateUtil.stampToDate(dujson.getString("PH_DELIVE")));
        mirrorPhaseCOne.setPhLandcl(dujson.getString("PH_LANDCL"));

        mirrorPhaseCOne.setArchSet(dujson.getString("ARCH_SET"));
        mirrorPhaseCOne.setHardcSet(dujson.getString("HARDC_SET"));
        mirrorPhaseCOne.setLandsSet(dujson.getString("LANDS_SET"));
        mirrorPhaseCOne.setTaxTyp(dujson.getString("TAX_TYP"));
        int num =saveOrUpdateMirrorPhase(mirrorPhaseCOne,id,type,map);
        return  num;
    }

    /**
     * 操作C2数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCTwo(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseCTwo mirrorPhaseCTwo=new MirrorPhaseCTwo();
        mirrorPhaseCTwo.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCTwo.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCTwo.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCTwo.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCTwo.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCTwo.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCTwo.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCTwo.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseCTwo.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseCTwo.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCTwo.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseCTwo.setPhCname(dujson.getString("PH_CNAME"));
        mirrorPhaseCTwo.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseCTwo.setPrType(dujson.getString("PR_TYPE"));
        mirrorPhaseCTwo.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCTwo.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseCTwo.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseCTwo.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseCTwo.setPrBugets(dujson.getString("PR_BUGETS"));
        mirrorPhaseCTwo.setPhPrdlev(dujson.getString("PH_PRDLEV"));
        mirrorPhaseCTwo.setPhDevlev(dujson.getString("PH_DEVLEV"));
        mirrorPhaseCTwo.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseCTwo.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));
        mirrorPhaseCTwo.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseCTwo.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseCTwo.setPhOtypD(dujson.getString("PH_OTYP_D"));
        mirrorPhaseCTwo.setPhOtypO(dujson.getString("PH_OTYP_O"));
        mirrorPhaseCTwo.setPhEqRD(dujson.getString("PH_EQ_R_D"));
        mirrorPhaseCTwo.setPhEqRX(dujson.getString("PH_EQ_R_X"));
        mirrorPhaseCTwo.setPhEqRT(dujson.getString("PH_EQ_R_T"));
        mirrorPhaseCTwo.setPhEqRO(dujson.getString("PH_EQ_R_O"));
        mirrorPhaseCTwo.setPhEqOX(dujson.getString("PH_EQ_O_X"));
        mirrorPhaseCTwo.setPhEqOT(dujson.getString("PH_EQ_O_T"));
        mirrorPhaseCTwo.setPhPrdlin(dujson.getString("PH_PRDLIN"));
        mirrorPhaseCTwo.setPhInterv(dujson.getString("PH_INTERV"));
        mirrorPhaseCTwo.setPhBustyp(dujson.getString("PH_BUSTYP"));
        mirrorPhaseCTwo.setPhAsstyp(dujson.getString("PH_ASSTYP"));
        mirrorPhaseCTwo.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseCTwo.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseCTwo.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseCTwo.setPhOpenD(DateUtil.stampToDate(dujson.getString("PH_OPEN_D")));
        mirrorPhaseCTwo.setPhLandcl(dujson.getString("PH_LANDCL"));
        mirrorPhaseCTwo.setTaxTyp(dujson.getString("TAX_TYP"));
        mirrorPhaseCTwo.setPhMeFlg(dujson.getString("PH_ME_FLG"));
        mirrorPhaseCTwo.setPhMcFlg(dujson.getString("PH_MC_FLG"));
        mirrorPhaseCTwo.setPhToFlg(dujson.getString("PH_TO_FLG"));
        mirrorPhaseCTwo.setPhMaFlg(dujson.getString("PH_MA_FLG"));

        mirrorPhaseCTwo.setPhDevsta(dujson.getString("PH_DEVSTA"));


        int num=saveOrUpdateMirrorPhase(mirrorPhaseCTwo,id,type,map);

        return  num;
    }

    /**
     * 操作C3数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCThree(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseCThree mirrorPhaseCThree=new MirrorPhaseCThree();
        mirrorPhaseCThree.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCThree.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCThree.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCThree.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCThree.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCThree.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCThree.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCThree.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseCThree.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseCThree.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCThree.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseCThree.setPrType(dujson.getString("PR_TYPE"));
        mirrorPhaseCThree.setPrCompan(dujson.getString("PR_COMPAN"));

        mirrorPhaseCThree.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCThree.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseCThree.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));
        mirrorPhaseCThree.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseCThree.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseCThree.setPhOptyp(dujson.getString("PH_OPTYP"));

        mirrorPhaseCThree.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseCThree.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseCThree.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseCThree.setPhOtypD(dujson.getString("PH_OTYP_D"));
        mirrorPhaseCThree.setPhOtypO(dujson.getString("PH_OTYP_O"));
        mirrorPhaseCThree.setPhEqRD(dujson.getString("PH_EQ_R_D"));
        mirrorPhaseCThree.setPhEqRX(dujson.getString("PH_EQ_R_X"));
        mirrorPhaseCThree.setPhEqRT(dujson.getString("PH_EQ_R_T"));
        mirrorPhaseCThree.setPhEqRO(dujson.getString("PH_EQ_R_O"));
        mirrorPhaseCThree.setPhEqOX(dujson.getString("PH_EQ_O_X"));
        mirrorPhaseCThree.setPhEqOT(dujson.getString("PH_EQ_O_T"));
        mirrorPhaseCThree.setPhPrdlin(dujson.getString("PH_PRDLIN"));
        mirrorPhaseCThree.setPhLandcl(dujson.getString("PH_LANDCL"));
        mirrorPhaseCThree.setTaxTyp(dujson.getString("TAX_TYP"));
        mirrorPhaseCThree.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseCThree.setPhAsstyp(dujson.getString("PH_ASSTYP"));
        mirrorPhaseCThree.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseCThree.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseCThree.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseCThree.setPhOpenD(DateUtil.stampToDate(dujson.getString("PH_OPEN_D")));
        mirrorPhaseCThree.setPrBugets(dujson.getString("PR_BUGETS"));

        mirrorPhaseCThree.setPhRnlev(dujson.getString("PH_RNLEV"));
        mirrorPhaseCThree.setPhFlplan(dujson.getString("PH_FLPLAN"));

        mirrorPhaseCThree.setPhLeaper(Integer.parseInt(dujson.getString("PH_LEAPER")));
        mirrorPhaseCThree.setPhRenSd(DateUtil.stampToDate(dujson.getString("PH_REN_SD")));
        mirrorPhaseCThree.setPhRenEd(DateUtil.stampToDate(dujson.getString("PH_REN_ED")));
        mirrorPhaseCThree.setPhShpadr(dujson.getString("PH_SHPADR"));
        mirrorPhaseCThree.setPhOpname(dujson.getString("PH_OPNAME"));
        mirrorPhaseCThree.setPhOpid(dujson.getString("PH_OPID"));
        mirrorPhaseCThree.setConstype(dujson.getString("CONSTYPE"));
        mirrorPhaseCThree.setPhShFlg(dujson.getString("PH_SH_FLG"));

        int num=saveOrUpdateMirrorPhase(mirrorPhaseCThree,id,type,map);

        return  num;
    }

    /**
     * 操作C4数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCFour(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseCFour mirrorPhaseCFour = new MirrorPhaseCFour();
        mirrorPhaseCFour.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseCFour.setSapver(dujson.getString("SAP_VER"));
        mirrorPhaseCFour.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCFour.setPrCom(dujson.getString("PR_COM"));
        mirrorPhaseCFour.setPrName(dujson.getString("PR_NAME"));
        mirrorPhaseCFour.setOldprId(dujson.getString("OLDPR_ID"));
        mirrorPhaseCFour.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCFour.setPrAddr(dujson.getString("PR_ADDR"));
        mirrorPhaseCFour.setPrDistr(dujson.getString("PR_DISTR"));
        mirrorPhaseCFour.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseCFour.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));
        mirrorPhaseCFour.setTakeDate(dujson.getString("TAKE_DATE"));
        mirrorPhaseCFour.setPrType(dujson.getString("PR_TYPE"));
        mirrorPhaseCFour.setServlev(dujson.getString("SERVLEV"));
        mirrorPhaseCFour.setTakeType(dujson.getString("TAKE_TYPE"));
        mirrorPhaseCFour.setDeFlg(dujson.getString("DE_FLG"));

        int num=saveOrUpdateMirrorPhase(mirrorPhaseCFour,id,type,map);
        return  num;

    }

    /**
     * 操作 业务分期表
     * @param object
     * @param type
     * @param map
     * @return
     */
    private  int saveOrUpdateAdptPhase(Object object,String type,Map<String,Object>map){

        AdptPhase adptPhase=new AdptPhase();

        AdptProj adptProj= new AdptProj();

        if(object instanceof MirrorPhaseCOne){
            MirrorPhaseCOne mirrorPhaseCOne=(MirrorPhaseCOne) object;
            adptProj= adptProjService.getByPrCode(mirrorPhaseCOne.getHisPrId());//根据项目code获取项目信息
            adptPhase=adptPhaseService.selectByFqXmCode(mirrorPhaseCOne.getHisCode(),adptProj.getLhXmcode());
            if(adptPhase==null){
                adptPhase=new AdptPhase();
            }
            adptPhase.setLhFqname(mirrorPhaseCOne.getTreePhm());
            adptPhase.setLhFqcode(mirrorPhaseCOne.getHisCode());
        }
        if(object instanceof MirrorPhaseCTwo){
            MirrorPhaseCTwo mirrorPhaseCTwo=(MirrorPhaseCTwo) object;
            adptProj= adptProjService.getByPrCode(mirrorPhaseCTwo.getHisPrId());//根据项目code获取项目信息
            adptPhase=adptPhaseService.selectByFqXmCode(mirrorPhaseCTwo.getHisCode(),adptProj.getLhXmcode());
            if(adptPhase==null){
                adptPhase=new AdptPhase();
            }
            adptPhase.setLhFqname(mirrorPhaseCTwo.getTreePhm());
            adptPhase.setLhFqcode(mirrorPhaseCTwo.getHisCode());
        }

        if(object instanceof MirrorPhaseCThree){
            MirrorPhaseCThree mirrorPhaseCTwo=(MirrorPhaseCThree) object;
            adptProj= adptProjService.getByPrCode(mirrorPhaseCTwo.getHisPrId());//根据项目code获取项目信息
            adptPhase=adptPhaseService.selectByFqXmCode(mirrorPhaseCTwo.getHisCode(),adptProj.getLhXmcode());
            if(adptPhase==null){
                adptPhase=new AdptPhase();
            }
            adptPhase.setLhFqname(mirrorPhaseCTwo.getTreePhm());
            adptPhase.setLhFqcode(mirrorPhaseCTwo.getHisCode());
        }

        adptPhase.setGroupId(4);
        adptPhase.setLhXmcode(adptProj.getLhXmcode());
        adptPhase.setLhGscode(adptProj.getLhGscode());
        adptPhase.setMenuType("1");
        adptPhase.setCompanyId(Integer.parseInt(String.valueOf(map.get(adptProj.getLhGscode()))));

        int num=-1;
        if(OperationEnum.ADD.getType().equals(type)){
            adptPhase.setCreateAt(new Date());
            adptPhase.setUpdateAt(new Date());
            num=adptPhaseService.insert(adptPhase);
        }else if(OperationEnum.UPDATE.getType().equals(type)){
            adptPhase.setUpdateAt(new Date());
            num=adptPhaseService.update(adptPhase);
        }else{
            adptPhase.setDeleteAt(new Date());
            num=adptPhaseService.update(adptPhase);
        }
        return num;
    }

    /**
     * 操作 分期过程表
     * @param object
     * @param id
     * @param type
     * @param map
     * @return
     */
    private int saveOrUpdateMirrorPhase(Object object,int id,String type,Map<String,Object>map){
        int num =-1;
        if(object instanceof MirrorPhaseCOne){
            MirrorPhaseCOne mirrorPhaseCOne=(MirrorPhaseCOne) object;

            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseCOneService.insert(mirrorPhaseCOne);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCOne.setId(id);
                num= mirrorPhaseCOneService.update(mirrorPhaseCOne);
            }else{//删除
                mirrorPhaseCOne.setId(id);
                num= mirrorPhaseCOneService.update(mirrorPhaseCOne);
            }
            if(num!=-1){//如果操作过程表成功  则调用业务表接口 操作业务表
                num= saveOrUpdateAdptPhase(mirrorPhaseCOne,type,map);
            }
        }
        if(object instanceof MirrorPhaseCTwo){
            MirrorPhaseCTwo mirrorPhaseCTwo=(MirrorPhaseCTwo) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseCTwoService.insert(mirrorPhaseCTwo);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCTwo.setId(id);
                num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
            }else{//删除
                mirrorPhaseCTwo.setId(id);
                num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
            }
            if(num!=-1){
                num= saveOrUpdateAdptPhase(mirrorPhaseCTwo,type,map);
            }
        }

        if(object instanceof MirrorPhaseCThree){
            MirrorPhaseCThree mirrorPhaseCThree=(MirrorPhaseCThree) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseCThreeService.insert(mirrorPhaseCThree);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCThree.setId(id);
                num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
            }else{//删除
                mirrorPhaseCThree.setId(id);
                num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
            }
            if(num!=-1){
                num= saveOrUpdateAdptPhase(mirrorPhaseCThree,type,map);
            }
        }
        return  num;
    }

    /**
     * 操作  组团表
     * @param jsonArray
     * @param phId
     * @return
     */
    @Transactional
    private int saveOrUpdateAdptGroup(JSONArray jsonArray,String phId){

        List<AdptGroup> adptGroupList=new ArrayList<>();

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);

            AdptGroup adptGroup=new AdptGroup();
            adptGroup.setPhId(phId);
            adptGroup.setDeFlg(jsonObject.getString("DE_FLG"));
            adptGroup.setGrCname(jsonObject.getString("GR_NAME"));
            adptGroup.setGrId(jsonObject.getString("GR_ID"));
            adptGroup.setHisFlg(jsonObject.getString("HIS_FLG"));
            adptGroup.setHisId(jsonObject.getString("HIS_ID"));
            adptGroup.setGrName(jsonObject.getString("GR_NAME"));
            adptGroup.setCreateAt(new Date());
            adptGroup.setUpdateAt(new Date());
            adptGroupList.add(adptGroup);
        }

        adptGroupService.delByPhId(phId);//按照分期身份证删除组团数据

        int num= adptGroupService.insertList(adptGroupList);//批量插入组团数据

        return num;
    }


    /**
     * 把成功或者失败信息返回给主数据
     * @param body
     * @param error
     */
    private void executorCallBack(String body, boolean error) {
        RestTemplate restTemplate=new RestTemplate();
        String userAndPass = "LASUSER:LAS12345"; //测试环境(10.49)
        //String userAndPass = "LASUSER:$jLVrn^8"; //生产环境(10.37)
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " +  Base64Utility.encode(userAndPass.getBytes()));
        headers.add("Content-Type","text/plain;charset=UTF-8");
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        log.debug("body==" + body);
		/* body是Http消息体例如json串 */
        log.debug(mdmSuccess);
        log.debug(mdmError);
        if(error) {
            restTemplate.exchange(mdmError, HttpMethod.POST, entity, String.class);
        } else {
            restTemplate.exchange(mdmSuccess, HttpMethod.POST, entity, String.class);
        }

    }

    /**
     * 新增项目/分期 返回成功或者失败
     * @param num
     * @param name
     * @return
     */
    private JSONObject insertResultStatus( int num,String name ,JSONObject result){
        if(-1==num) {
            result.put("code", "500");
            result.put("message", name + "创建失败");
        }else {
            result.put("code", "200");
            result.put("message", name + "创建成功");
        }
        return result;
    }

    /**
     * 处理组团数据 返回成功或者失败
     * @param num
     * @return
     */
    private JSONObject saveOrUpdateGroupResultStatus( int num,JSONObject result){
        if(-1==num) {
            result.put("code", "500");
            result.put("message",  "处理组团数据失败");
        }else {
            result.put("code", "200");
            result.put("message", "处理组团数据成功");
        }
        return result;
    }

    /**
     * 修改航道 分期 返回成功或者失败
     * @param num
     * @param stageName
     * @return
     */
    private JSONObject updateResultStatus( int num,String stageName ,JSONObject result){
        if(-1==num) {
            result.put("code", "500");
            result.put("message", stageName + "修改失败");
        }else {
            result.put("code", "200");
            result.put("message", stageName + "修改成功");
        }
        return result;
    }

    /**
     * 删除航道 分期 返回成功或者失败
     * @param num
     * @param stageName
     * @return
     */
    private JSONObject deleteResultStatus( int num,String stageName ,JSONObject result) {
        if (-1 == num) {
            result.put("code", "500");
            result.put("message", stageName + "删除失败");
        } else {
            result.put("code", "200");
            result.put("message", stageName + "删除成功");
        }
        return result;
    }


}
