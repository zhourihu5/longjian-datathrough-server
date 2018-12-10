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

    @Resource
    private MirrorPhaseDOneService mirrorPhaseDOneService;

    @Resource
    private MirrorPhaseDTwoService mirrorPhaseDTwoService;

    @Resource
    private MirrorPhaseDThreeService mirrorPhaseDThreeService;

    @Resource
    private ZrreBstService zrreBstService;

    @Resource
    private ZrreTFmtAllService zrreTFmtAllService;


    /**
     * 同步项目
     * @param jsonObject
     */
    @Override
    public void excuteProject(JSONObject jsonObject) {

        JSONObject result = new JSONObject();
        JSONArray itemarry = jsonObject.getJSONArray("ItemArray"); //操作的数据集合
        JSONArray errorArray = new JSONArray();
        result.put("SystemID","LAS");
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
        result.put("SystemID", "LAS");
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
     * 同步业态字典数据
     * @param jsonObject
     */
    @Override
    public void excuteDic(JSONObject jsonObject) {
        log.debug(JSON.toJSONString(jsonObject));
        JSONArray jsonArray=jsonObject.getJSONArray("ConfigArray");
        List<ZrreTFmtAll>zrreTFmtAllList=new ArrayList<>();
        List<ZrreBst>zrreBstList=new ArrayList<>();
        if(jsonArray!=null && jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String tableName = object.getString("TableName");
                JSONArray fmtArr = object.getJSONArray("JsonString");
                if (fmtArr != null && fmtArr.size() > 0) {
                    if ("ZRRE_T_FMT_ALL".equals(tableName)) {
                        for (int j = 0; j < fmtArr.size(); j++) {
                            JSONObject fmtObject = fmtArr.getJSONObject(j);
                            ZrreTFmtAll zrreTFmtAll = new ZrreTFmtAll();
                            zrreTFmtAll.setMandt(fmtObject.getString("MANDT"));
                            zrreTFmtAll.setDbKey(fmtObject.getString("DB_KEY"));
                            zrreTFmtAll.setChannelNum(fmtObject.getString("CHANNEL_NUM"));
                            zrreTFmtAll.setFmtFcls(fmtObject.getString("FMT_FCLS"));
                            zrreTFmtAll.setFmtFclsDesc(fmtObject.getString("FMT_FCLS_DESC"));
                            zrreTFmtAll.setFmtScls(fmtObject.getString("FMT_SCLS"));
                            zrreTFmtAll.setFmtSclsDesc(fmtObject.getString("FMT_SCLS_DESC"));
                            zrreTFmtAll.setFmtTcls(fmtObject.getString("FMT_TCLS"));
                            zrreTFmtAll.setFmtTclsDesc(fmtObject.getString("FMT_TCLS_DESC"));
                            zrreTFmtAll.setFmtAttr(fmtObject.getString("FMT_ATTR"));
                            zrreTFmtAll.setFmtAttrDesc(fmtObject.getString("FMT_ATTR_DESC"));
                            zrreTFmtAll.setAttrValue(fmtObject.getString("ATTR_VALUE"));
                            zrreTFmtAll.setAttrValueDesc(fmtObject.getString("ATTR_VALUE_DESC"));
                            zrreTFmtAll.setFmtComb(fmtObject.getString("FMT_COMB"));
                            zrreTFmtAll.setFmtCombDesc(fmtObject.getString("FMT_COMB_DESC"));
                            zrreTFmtAll.setFmtAttr1(fmtObject.getString("FMT_ATTR_1"));
                            zrreTFmtAll.setFmtAttr2(fmtObject.getString("FMT_ATTR_2"));
                            zrreTFmtAll.setAttrValue1(fmtObject.getString("ATTR_VALUE1"));
                            zrreTFmtAll.setAttrValueDesc1(fmtObject.getString("ATTR_VALUE_DESC1"));
                            zrreTFmtAll.setAttrValue2(fmtObject.getString("ATTR_VALUE2"));
                            zrreTFmtAll.setAttrValueDesc2(fmtObject.getString("ATTR_VALUE_DESC2"));
                            zrreTFmtAll.setAttrValue3(fmtObject.getString("ATTR_VALUE3"));
                            zrreTFmtAll.setAttrValueDesc3(fmtObject.getString("ATTR_VALUE_DESC3"));
                            zrreTFmtAllList.add(zrreTFmtAll);
                        }
                    } else if ("ZRRE_BST0002".equals(tableName)) {
                        for (int j = 0; j < fmtArr.size(); j++) {
                            JSONObject fmtObject = fmtArr.getJSONObject(j);
                            ZrreBst zrreBst = new ZrreBst();
                            zrreBst.setMandt(fmtObject.getString("MANDT"));
                            zrreBst.setNamee(fmtObject.getString("NAMEE"));
                            zrreBst.setNamej(fmtObject.getString("NAMEJ"));
                            zrreBst.setNodeid(fmtObject.getString("NODEID"));
                            zrreBst.setZcity(fmtObject.getString("ZCITY"));
                            zrreBst.setZcityName(fmtObject.getString("ZCITY_NAME"));
                            zrreBst.setZnode(fmtObject.getString("ZNODE"));
                            zrreBst.setZnodeName(fmtObject.getString("ZNODE_NAME"));
                            zrreBst.setZtreeVer(fmtObject.getString("ZTREE_VER"));
                            zrreBstList.add(zrreBst);
                        }
                    }
                }
                if (zrreTFmtAllList.size() > 0) {
                    zrreTFmtAllService.insertList(zrreTFmtAllList);
                }
                if (zrreBstList.size() > 0) {
                    zrreBstService.insertList(zrreBstList);
                }
            }
        }

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
                num=saveOrupdateAptProject(adptProj,projCode,projName,gsCode,OperationEnum.UPDATE.getValue(),map);
                result=updateResultStatus(num,projName,result);
            }else {
                num=saveOrupdateAptProject(adptProj,projCode,projName,gsCode,OperationEnum.ADD.getValue(),map);
                result=insertResultStatus(num,projName,result);
            }
        } else if("U".equals(operCode)) { // 更新
            if(adptProj==null){
                num=saveOrupdateAptProject(adptProj,projCode,projName,gsCode,OperationEnum.ADD.getValue(),map);
                result=insertResultStatus(num,projName,result);
            }else{
                num=saveOrupdateAptProject(adptProj,projCode,projName,gsCode,OperationEnum.UPDATE.getValue(),map);
                result=updateResultStatus(num,projName,result);
            }
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
     * 保存或者更新项目数据
     * @param adptProj
     * @param projName
     * @param gsCode
     */
    private int  saveOrupdateAptProject(AdptProj adptProj,String projCode,String projName,String gsCode,String type,Map<String,Object>map){
        int num=0;
        if(OperationEnum.ADD.getValue().equals(type)){
            adptProj=new AdptProj();
            adptProj.setLhXmcode(projCode);
            adptProj.setLhXmname(projName);
            adptProj.setLhGscode(gsCode);
            adptProj.setCompanyId(Integer.parseInt(String.valueOf(map.get(gsCode))));
            adptProj.setGroupId(4);
            adptProj.setMenuType("1");
            adptProj.setCreateAt(new Date());
            adptProj.setUpdateAt(new Date());
            num= adptProjService.createAdptProj(adptProj);

        }else {
            adptProj.setLhXmname(projName);
            adptProj.setLhGscode(gsCode);
            adptProj.setUpdateAt(new Date());
            num = adptProjService.updateAdptProj(adptProj);
        }
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

            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增或者更新  不管operCode是C还是U  只要数据库不存在同样的数据  就新增  否则就更新
                if (oldMirrorPhaseCOne != null) {
                    num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCOne.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {//删除
                num = saveOrUpdateMirrorPhaseCOne(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCOne.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }

        }else if("C2".equals(buId)) {//如果航道是C2
            MirrorPhaseCTwo oldMirrorPhaseCTwo = mirrorPhaseCTwoService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseCTwo != null) {
                    num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCTwo.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCTwo(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCTwo.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("C3".equals(buId)) {//如果航道是C3
            MirrorPhaseCThree oldMirrorPhaseCThree = mirrorPhaseCThreeService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseCThree != null) {
                    num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCThree.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCThree(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCThree.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("C4".equals(buId)) {//如果航道是C4
            MirrorPhaseCFour oldMirrorPhaseCFour = mirrorPhaseCFourService.findByPhIdSapVer(prId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseCFour != null) {
                    num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseCFour.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseCFour(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseCFour.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("D1".equals(buId)) {//如果航道是D1
            MirrorPhaseDOne oldMirrorPhaseDOne = mirrorPhaseDOneService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseDOne != null) {
                    num = saveOrUpdateMirrorPhaseDOne(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseDOne.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseDOne(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseDOne(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseDOne.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("D2".equals(buId)) {//如果航道是D2
            MirrorPhaseDTwo oldMirrorPhaseDTwo = mirrorPhaseDTwoService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseDTwo != null) {
                    num = saveOrUpdateMirrorPhaseDTwo(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseDTwo.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseDTwo(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseDTwo(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseDTwo.getId(),map);//删除操作
                result = deleteResultStatus(num, stageName, result);
            }
        }else if("D3".equals(buId)) {//如果航道是D3
            MirrorPhaseDThree oldMirrorPhaseDThree = mirrorPhaseDThreeService.findByPhIdSapVer(phId, sapVer);//判断 同一个分期 同一个版本是否推送过
            if ("C".equals(operCode)||"U".equals(operCode)) { // 新增
                if (oldMirrorPhaseDThree != null) {
                    num = saveOrUpdateMirrorPhaseDThree(dujson, OperationEnum.UPDATE.getType(), oldMirrorPhaseDThree.getId(),map);//更新操作
                    result = updateResultStatus(num, stageName, result);
                } else {
                    num = saveOrUpdateMirrorPhaseDThree(dujson, OperationEnum.ADD.getType(), 0,map);//新增操作
                    result = insertResultStatus(num, stageName, result);
                }
            }else if ("D".equals(operCode)) {
                num = saveOrUpdateMirrorPhaseDThree(dujson, OperationEnum.DEL.getType(), oldMirrorPhaseDThree.getId(),map);//删除操作
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
     * 操作D1数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseDOne(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseDOne mirrorPhaseDOne=new MirrorPhaseDOne();
        mirrorPhaseDOne.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseDOne.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseDOne.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseDOne.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseDOne.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseDOne.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseDOne.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseDOne.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseDOne.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseDOne.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseDOne.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseDOne.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseDOne.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseDOne.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseDOne.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseDOne.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseDOne.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));

        mirrorPhaseDOne.setHotelName(dujson.getString("HOTELNAME"));
        mirrorPhaseDOne.setPhAsstyp(dujson.getString("PH_ASSTYP"));
        mirrorPhaseDOne.setHotelAddress(dujson.getString("HOTELADDR"));
        mirrorPhaseDOne.setPhOpenD(DateUtil.stampToDate(dujson.getString("PH_OPEN_D")));
        mirrorPhaseDOne.setHotelRes(dujson.getString("HOTELRES"));
        mirrorPhaseDOne.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseDOne.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseDOne.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseDOne.setHotelBran(dujson.getString("HOTELBRAN"));
        mirrorPhaseDOne.setHotelStar(dujson.getString("HOTELSTAR"));
        mirrorPhaseDOne.setPhOtypD(dujson.getString("PH_OTYP_D"));
        mirrorPhaseDOne.setPhOtypO(dujson.getString("PH_OTYP_O"));
        mirrorPhaseDOne.setPhEqRD(dujson.getString("PH_EQ_R_D"));
        mirrorPhaseDOne.setPhEqRX(dujson.getString("PH_EQ_R_X"));
        mirrorPhaseDOne.setPhEqRT(dujson.getString("PH_EQ_R_T"));
        mirrorPhaseDOne.setPhEqRO(dujson.getString("PH_EQ_R_O"));
        mirrorPhaseDOne.setPhEqOX(dujson.getString("PH_EQ_O_X"));
        mirrorPhaseDOne.setPhEqOT(dujson.getString("PH_EQ_O_T"));

        mirrorPhaseDOne.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseDOne.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseDOne.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseDOne.setPrBugets(dujson.getString("PR_BUGETS"));
        mirrorPhaseDOne.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseDOne.setPrCompan(dujson.getString("PR_COMPAN"));

        mirrorPhaseDOne.setPhShFlg(dujson.getString("PH_SH_FLG"));
        mirrorPhaseDOne.setManageTyp(dujson.getString("MANAGETYP"));
        mirrorPhaseDOne.setContStage(dujson.getString("CONTSTAGE"));
        mirrorPhaseDOne.setHotelOcat(dujson.getString("HOTELOCAT"));
        mirrorPhaseDOne.setPhMcFlg(dujson.getString("PH_MC_FLG"));
        mirrorPhaseDOne.setChargFlg(dujson.getString("CHARG_FLG"));
        mirrorPhaseDOne.setHotelScal(dujson.getString("HOTELSCAL"));
        mirrorPhaseDOne.setHotelAyou(dujson.getString("HOTELAYOU"));
        mirrorPhaseDOne.setConstype(dujson.getString("CONSTYPE"));
        mirrorPhaseDOne.setPhLandcl(dujson.getString("PH_LANDCL"));

        mirrorPhaseDOne.setTaxTyp(dujson.getString("TAX_TYP"));
        int num =saveOrUpdateMirrorPhase(mirrorPhaseDOne,id,type,map);
        return  num;
    }

    /**
     * 操作D2数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseDTwo(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {


        MirrorPhaseDTwo mirrorPhaseDTwo=new MirrorPhaseDTwo();
        mirrorPhaseDTwo.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseDTwo.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseDTwo.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseDTwo.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseDTwo.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseDTwo.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseDTwo.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseDTwo.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseDTwo.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseDTwo.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseDTwo.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseDTwo.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseDTwo.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseDTwo.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseDTwo.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseDTwo.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseDTwo.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));

        mirrorPhaseDTwo.setPhAsstyp(dujson.getString("PH_ASSTYP"));
        mirrorPhaseDTwo.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseDTwo.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseDTwo.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseDTwo.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseDTwo.setTaxTyp(dujson.getString("TAX_TYP"));
        mirrorPhaseDTwo.setPhLandcl(dujson.getString("PH_LANDCL"));

        mirrorPhaseDTwo.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseDTwo.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseDTwo.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseDTwo.setRevTyp(dujson.getString("REV_TYP"));
        mirrorPhaseDTwo.setOpComName(dujson.getString("OPCOMNAME"));
        mirrorPhaseDTwo.setPrBugets(dujson.getString("PR_BUGETS"));


        mirrorPhaseDTwo.setPhOtypD(dujson.getString("PH_OTYP_D"));
        mirrorPhaseDTwo.setPhOtypO(dujson.getString("PH_OTYP_O"));
        mirrorPhaseDTwo.setPhEqRD(dujson.getString("PH_EQ_R_D"));
        mirrorPhaseDTwo.setPhEqRX(dujson.getString("PH_EQ_R_X"));
        mirrorPhaseDTwo.setPhEqRT(dujson.getString("PH_EQ_R_T"));
        mirrorPhaseDTwo.setPhEqRO(dujson.getString("PH_EQ_R_O"));
        mirrorPhaseDTwo.setPhEqOX(dujson.getString("PH_EQ_O_X"));
        mirrorPhaseDTwo.setPhEqOT(dujson.getString("PH_EQ_O_T"));

        mirrorPhaseDTwo.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseDTwo.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseDTwo.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseDTwo.setPhOpmode(dujson.getString("PH_OPMODE"));
        mirrorPhaseDTwo.setPhPrdlin(dujson.getString("PH_PRDLIN"));
        mirrorPhaseDTwo.setPrStage(dujson.getString("PR_STAGE"));
        mirrorPhaseDTwo.setOpperiod(dujson.getString("OPPERIOD"));
        mirrorPhaseDTwo.setPhDevlev(dujson.getString("PH_DEVLEV"));
        mirrorPhaseDTwo.setPropertyp(dujson.getString("PROPERTYP"));

        int num =saveOrUpdateMirrorPhase(mirrorPhaseDTwo,id,type,map);
        return  num;
    }

    /**
     * 操作D3数据
     * @param dujson
     * @param type  0是新增  1是修改 其他是删除
     * @param id 旧数据主键
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseDThree(JSONObject dujson,String type,int id,Map<String,Object>map) throws ParseException {

        MirrorPhaseDThree mirrorPhaseDThree=new MirrorPhaseDThree();
        mirrorPhaseDThree.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseDThree.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseDThree.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseDThree.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseDThree.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseDThree.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseDThree.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseDThree.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseDThree.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseDThree.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseDThree.setTreePhm(dujson.getString("TREE_PHM"));
        mirrorPhaseDThree.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseDThree.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseDThree.setHisPrId(dujson.getString("HIS_PRID"));
        mirrorPhaseDThree.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseDThree.setCrDate(DateUtil.stampToDate(dujson.getString("CR_DATE")));
        mirrorPhaseDThree.setChDate(DateUtil.stampToDate(dujson.getString("CH_DATE")));

        mirrorPhaseDThree.setPhAsstyp(dujson.getString("PH_ASSTYP"));
        mirrorPhaseDThree.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseDThree.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseDThree.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseDThree.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseDThree.setTaxTyp(dujson.getString("TAX_TYP"));
        mirrorPhaseDThree.setPhLandcl(dujson.getString("PH_LANDCL"));

        mirrorPhaseDThree.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseDThree.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseDThree.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseDThree.setRevTyp(dujson.getString("REV_TYP"));
        mirrorPhaseDThree.setOpComName(dujson.getString("OPCOMNAME"));
        mirrorPhaseDThree.setPrLoc(dujson.getString("项目区位"));

        mirrorPhaseDThree.setPhOtypD(dujson.getString("PH_OTYP_D"));
        mirrorPhaseDThree.setPhOtypO(dujson.getString("PH_OTYP_O"));
        mirrorPhaseDThree.setPhEqRD(dujson.getString("PH_EQ_R_D"));
        mirrorPhaseDThree.setPhEqRX(dujson.getString("PH_EQ_R_X"));
        mirrorPhaseDThree.setPhEqRT(dujson.getString("PH_EQ_R_T"));
        mirrorPhaseDThree.setPhEqRO(dujson.getString("PH_EQ_R_O"));
        mirrorPhaseDThree.setPhEqOX(dujson.getString("PH_EQ_O_X"));
        mirrorPhaseDThree.setPhEqOT(dujson.getString("PH_EQ_O_T"));

        mirrorPhaseDThree.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseDThree.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseDThree.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseDThree.setPhOpmode(dujson.getString("PH_OPMODE"));
        mirrorPhaseDThree.setPhPrdlin(dujson.getString("PH_PRDLIN"));
        mirrorPhaseDThree.setPropeFlg(dujson.getString("PROPE_FLG"));
        mirrorPhaseDThree.setPropertyp(dujson.getString("PROPERTYP"));

        int num =saveOrUpdateMirrorPhase(mirrorPhaseDThree,id,type,map);
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
            }else{
                type=OperationEnum.UPDATE.getType();
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
            }else{
                type=OperationEnum.UPDATE.getType();
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
            }else{
                type=OperationEnum.UPDATE.getType();
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
                mirrorPhaseCOne.setCreateAt(new Date());
                num= mirrorPhaseCOneService.insert(mirrorPhaseCOne);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCOne.setId(id);
                mirrorPhaseCOne.setUpdateAt(new Date());
                num= mirrorPhaseCOneService.update(mirrorPhaseCOne);
            }else{//删除
                mirrorPhaseCOne.setId(id);
                mirrorPhaseCOne.setUpdateAt(new Date());
                num= mirrorPhaseCOneService.update(mirrorPhaseCOne);
            }
            if(num!=-1){//如果操作过程表成功  则调用业务表接口 操作业务表
                num= saveOrUpdateAdptPhase(mirrorPhaseCOne,type,map);
            }
        }
        if(object instanceof MirrorPhaseCTwo){
            MirrorPhaseCTwo mirrorPhaseCTwo=(MirrorPhaseCTwo) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                mirrorPhaseCTwo.setCreateAt(new Date());
                num= mirrorPhaseCTwoService.insert(mirrorPhaseCTwo);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCTwo.setId(id);
                mirrorPhaseCTwo.setUpdateAt(new Date());
                num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
            }else{//删除
                mirrorPhaseCTwo.setId(id);
                mirrorPhaseCTwo.setUpdateAt(new Date());
                num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
            }
            if(num!=-1){
                num= saveOrUpdateAdptPhase(mirrorPhaseCTwo,type,map);
            }
        }

        if(object instanceof MirrorPhaseCThree){
            MirrorPhaseCThree mirrorPhaseCThree=(MirrorPhaseCThree) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                mirrorPhaseCThree.setUpdateAt(new Date());
                num= mirrorPhaseCThreeService.insert(mirrorPhaseCThree);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseCThree.setId(id);
                mirrorPhaseCThree.setUpdateAt(new Date());
                num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
            }else{//删除
                mirrorPhaseCThree.setId(id);
                mirrorPhaseCThree.setUpdateAt(new Date());
                num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
            }
            if(num!=-1){
                num= saveOrUpdateAdptPhase(mirrorPhaseCThree,type,map);
            }
        }if(object instanceof MirrorPhaseDOne){
            MirrorPhaseDOne mirrorPhaseDOne=(MirrorPhaseDOne) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseDOneService.insert(mirrorPhaseDOne);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseDOne.setId(id);
                num= mirrorPhaseDOneService.update(mirrorPhaseDOne);
            }else{//删除
                mirrorPhaseDOne.setId(id);
                num= mirrorPhaseDOneService.update(mirrorPhaseDOne);
            }
        }if(object instanceof MirrorPhaseDTwo){
            MirrorPhaseDTwo mirrorPhaseDTwo=(MirrorPhaseDTwo) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseDTwoService.insert(mirrorPhaseDTwo);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseDTwo.setId(id);
                num= mirrorPhaseDTwoService.update(mirrorPhaseDTwo);
            }else{//删除
                mirrorPhaseDTwo.setId(id);
                num= mirrorPhaseDTwoService.update(mirrorPhaseDTwo);
            }
        }if(object instanceof MirrorPhaseDThree){
            MirrorPhaseDThree mirrorPhaseDThree=(MirrorPhaseDThree) object;
            if(OperationEnum.ADD.getType().equals(type)){//新增
                num= mirrorPhaseDThreeService.insert(mirrorPhaseDThree);
            }else if(OperationEnum.UPDATE.getType().equals(type)) {//修改
                mirrorPhaseDThree.setId(id);
                num= mirrorPhaseDThreeService.update(mirrorPhaseDThree);
            }else{//删除
                mirrorPhaseDThree.setId(id);
                num= mirrorPhaseDThreeService.update(mirrorPhaseDThree);
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
            adptGroup.setBanquFlg(jsonObject.getString("BANQU_FLG"));
            adptGroup.setSwimcond(jsonObject.getString("SWIMCOND"));
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
