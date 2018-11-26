package com.longfor.longjian.datathrough.app.appService.master.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longfor.longjian.datathrough.app.appService.master.MasterService;
import com.longfor.longjian.datathrough.domain.innerService.*;
import com.longfor.longjian.datathrough.po.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
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


    /**
     * 同步项目
     * @param systemId
     * @param data
     */
    @Override
    public void excuteProject(String systemId, String data) {
        JSONObject json = JSONArray.parseObject(data);
        JSONObject result = new JSONObject();
        JSONArray itemarry = json.getJSONArray("ItemArray"); //操作的数据集合
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
     * @param systemId
     * @param data
     */
    @Override
    public void excuteStage(String systemId, String data) {

        JSONObject json = JSON.parseObject(data);
        JSONArray itemarry = json.getJSONArray("ItemArray");
        JSONObject result = new JSONObject();
        result.put("SystemID", "PPS");
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
    private JSONObject operProject(JSONObject project,Map<String,Object>map) {
        JSONObject result = new JSONObject();
        String operCode = project.getString("OP_CODE").trim();//操作码
        String projName = project.getString("PR_NAME").trim();//项目名称
        String projCode=project.getString("HIS_CODE").trim();//项目历史code
        String gsCode=project.getString("PR_REGID").trim();//公司code
        AdptProj adptProj=adptProjService.getByPrCode(projCode);
        if("C".equals(operCode)) { // 新增
                if (adptProj != null ) { // 项目已经存在
                    if (adptProj.getLhXmname().equals(projName) == false) { // 名称更新了
                        updateAptProject(adptProj,projName,gsCode);
                        result.put("code", "200");
                        result.put("message", projName + "修改成功");
                    } else {
                        result.put("code", "400");
                        result.put("message", projName + "项目已存在");
                    }
                    return result;
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
                   int num= adptProjService.createAdptProj(newAdptProj);

                   insertResultStatus(num,projName,result);
                   
                }
        } else if("U".equals(operCode)) { // 更新

            if (adptProj != null&&adptProj.getLhXmname().equals(projName) == false) { // 名称更新了
                updateAptProject(adptProj,projName,gsCode);
                result.put("code", "200");
                result.put("message", projName + "修改成功");
            }else {
                result.put("code", "400");
                result.put("message", projName + " 名称和CODE无更新");
            }
        } else if("D".equals(operCode)) { // 删除
            if (adptProj != null ) {
                adptProj.setDeleteAt(new Date());
                adptProjService.updateAdptProj(adptProj);
            }
            result.put("code", "200");
            result.put("message", projName + "删除成功");
        }
        return result;
    }


    /**
     * 更新项目数据
     * @param adptProj
     * @param projName
     * @param gsCode
     */
    private void  updateAptProject(AdptProj adptProj,String projName,String gsCode){
            adptProj.setLhXmname(projName);
            adptProj.setLhGscode(gsCode);
            adptProj.setUpdateAt(new Date());
            adptProjService.updateAdptProj(adptProj);
    }


    /**
     *按航道 处理分期数据
     * */
    private JSONObject operStage(JSONObject dujson) throws ParseException {

        String operCode = dujson.getString("OP_CODE").trim();
        String buId = dujson.getString("BU_ID").trim();//航道
        String stageName=dujson.getString("PH_NAME");
        String deFlg=dujson.getString("DE_FLG");//删除标记
        JSONObject result = new JSONObject();

        if("C".equals(operCode)) { // 新增

            if("C1".equals(buId)){//如果航道是C1
              MirrorPhaseCOne oldMirrorPhaseCOne=  mirrorPhaseCOneService.findByBuId(buId);
              if(oldMirrorPhaseCOne!=null){
                  result.put("code", "400");
                  result.put("message", oldMirrorPhaseCOne.getPhName() + "已存在");
              }else{
                  int num =saveOrUpdateMirrorPhaseCOne(dujson,0,0,null);

                  insertResultStatus(num,stageName,result);

              }
            }else if("C2".equals(buId)){//如果航道是C2
                MirrorPhaseCTwo oldMirrorPhaseCTwo=  mirrorPhaseCTwoService.findByBuId(buId);
                if(oldMirrorPhaseCTwo!=null){
                    result.put("code", "400");
                    result.put("message", oldMirrorPhaseCTwo.getPhName() + "已存在");
                }else{
                    int num =saveOrUpdateMirrorPhaseCTwo(dujson,0,0,null);

                    insertResultStatus(num,stageName,result);
                }
            }else if("C3".equals(buId)){//如果航道是C3
                MirrorPhaseCThree oldMirrorPhaseCThree=  mirrorPhaseCThreeService.findByBuId(buId);
                if(oldMirrorPhaseCThree!=null){
                    result.put("code", "400");
                    result.put("message", oldMirrorPhaseCThree.getPhName() + "已存在");
                }else{
                    int num =saveOrUpdateMirrorPhaseCThree(dujson,0,0,null);

                    insertResultStatus(num,stageName,result);
                }
            }else if("C4".equals(buId)){//如果航道是C4
                MirrorPhaseCFour oldMirrorPhaseCFour=  mirrorPhaseCFourService.findByBuId(buId);
                if(oldMirrorPhaseCFour!=null){
                    result.put("code", "400");
                    result.put("message", oldMirrorPhaseCFour.getPrName() + "已存在");
                }else{
                    int num =saveOrUpdateMirrorPhaseCFour(dujson,0,0,null);

                    insertResultStatus(num,stageName,result);
                }
            }

        } else if("U".equals(operCode)) { // 更新分期
            if("C1".equals(buId)){

                MirrorPhaseCOne oldMirrorPhaseCOne=  mirrorPhaseCOneService.findByBuId(buId);

                int num =saveOrUpdateMirrorPhaseCOne(dujson,1,oldMirrorPhaseCOne.getId(),null);

                updateResultStatus(num,stageName,result);

            }if("C2".equals(buId)){

                MirrorPhaseCTwo oldMirrorPhaseCTwo=  mirrorPhaseCTwoService.findByBuId(buId);

                int num =saveOrUpdateMirrorPhaseCTwo(dujson,1,oldMirrorPhaseCTwo.getId(),null);

                updateResultStatus(num,stageName,result);

            }if("C3".equals(buId)){

                MirrorPhaseCThree oldMirrorPhaseCThree=  mirrorPhaseCThreeService.findByBuId(buId);

                int num =saveOrUpdateMirrorPhaseCThree(dujson,1,oldMirrorPhaseCThree.getId(),null);

                updateResultStatus(num,stageName,result);

            }if("C1".equals(buId)){

                MirrorPhaseCFour oldMirrorPhaseCFour=  mirrorPhaseCFourService.findByBuId(buId);

                int num =saveOrUpdateMirrorPhaseCFour(dujson,1,oldMirrorPhaseCFour.getId(),null);

                updateResultStatus(num,stageName,result);
            }

        } else if("D".equals(operCode)) {

            if("C1".equals(buId)) {

                MirrorPhaseCOne oldMirrorPhaseCOne = mirrorPhaseCOneService.findByBuId(buId);

                oldMirrorPhaseCOne.setDeFlg(deFlg);

                int num = saveOrUpdateMirrorPhaseCOne(dujson, 2, oldMirrorPhaseCOne.getId(), deFlg);

                deleteResultStatus(num,stageName,result);

            }else if("C2".equals(buId)) {

                MirrorPhaseCTwo oldMirrorPhaseCTwo = mirrorPhaseCTwoService.findByBuId(buId);

                oldMirrorPhaseCTwo.setDeFlg(deFlg);

                int num = saveOrUpdateMirrorPhaseCTwo(dujson, 2, oldMirrorPhaseCTwo.getId(), deFlg);

                deleteResultStatus(num,stageName,result);
            }else if("C3".equals(buId)) {

                MirrorPhaseCThree oldMirrorPhaseCThree = mirrorPhaseCThreeService.findByBuId(buId);

                oldMirrorPhaseCThree.setDeFlg(deFlg);

                int num = saveOrUpdateMirrorPhaseCThree(dujson, 2, oldMirrorPhaseCThree.getId(), deFlg);

                deleteResultStatus(num,stageName,result);

            }else if("C4".equals(buId)) {

                MirrorPhaseCFour oldMirrorPhaseCFour = mirrorPhaseCFourService.findByBuId(buId);

                oldMirrorPhaseCFour.setDeFlg(deFlg);

                int num = saveOrUpdateMirrorPhaseCFour(dujson, 2, oldMirrorPhaseCFour.getId(), deFlg);

                deleteResultStatus(num,stageName,result);
            }
        }
        JSONArray groups = dujson.getJSONArray("GroupArray");//组团

        // 处理分期下组团
        if(groups != null ) {
            for (int j = 0; j< groups.size(); j++) {
                /*JSONObject groupResult = operGroup(operCode, buId, groups.getJSONObject(j));
                if("200".equals(groupResult.getString("code"))) {
                    result.put("group:"+groups.getJSONObject(j).getString("GR_ID"), "200");
                } else {
                    result.put("group:"+groups.getJSONObject(j).getString("GR_ID"), "400");
                    result.put(groups.getJSONObject(j).getString("GR_ID"), groupResult.getString("message"));
                    if (result.has("message")) {
                        result.put("message", result.getString("message")+";"+groupResult.getString("message"));
                    } else {
                        result.put("message", groupResult.getString("message"));
                    }
                }*/
            }
        }

        return result;
    }

    /**
     * 新增C1 分期数据
     * @param dujson
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCOne(JSONObject dujson,int type,int id,String deFlg) throws ParseException {

        DateFormat dateFormatter = DateFormat.getDateTimeInstance();

        MirrorPhaseCOne mirrorPhaseCdc=new MirrorPhaseCOne();
        mirrorPhaseCdc.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCdc.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCdc.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCdc.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCdc.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCdc.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCdc.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCdc.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseCdc.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCdc.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseCdc.setPhCname(dujson.getString("PH_CNAME"));
        mirrorPhaseCdc.setPrCompan(dujson.getString("PR_COMPAN"));
        mirrorPhaseCdc.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCdc.setPhPrdlev(dujson.getString("PH_PRDLEV"));
        mirrorPhaseCdc.setPhDevlev(dujson.getString("PH_DEVLEV"));
        mirrorPhaseCdc.setCrDate(dateFormatter.parse(dujson.getString("CR_DATE")));
        mirrorPhaseCdc.setChDate(dateFormatter.parse(dujson.getString("CH_DATE")));
        mirrorPhaseCdc.setPrId(dujson.getString("PR_ID"));
        mirrorPhaseCdc.setPhOptyp(dujson.getString("PH_OPTYP"));
        mirrorPhaseCdc.setPhEquiR(dujson.getString("PH_EQUI_R"));
        mirrorPhaseCdc.setPhEquiX(dujson.getString("PH_EQUI_X"));
        mirrorPhaseCdc.setPhEquiT(dujson.getString("PH_EQUI_T"));
        mirrorPhaseCdc.setCaTyp(dujson.getString("CA_TYP"));
        mirrorPhaseCdc.setCaTypX(dujson.getString("CA_TYP_X"));
        mirrorPhaseCdc.setCaTypT(dujson.getString("CA_TYP_T"));
        mirrorPhaseCdc.setPrBugets(dujson.getString("PR_BUGETS"));
        mirrorPhaseCdc.setPhPrgets(dujson.getString("PH_PRGETS"));
        mirrorPhaseCdc.setPrGettyp(dujson.getString("PR_GETTYP"));
        mirrorPhaseCdc.setPhMaTyp(dujson.getString("PH_MA_TYP"));
        mirrorPhaseCdc.setBugetFlg(dujson.getString("BUGET_FLG"));
        mirrorPhaseCdc.setPhDelive(dateFormatter.parse(dujson.getString("PH_DELIVE")));
        mirrorPhaseCdc.setPhLandcl(dujson.getString("PH_LANDCL"));

        mirrorPhaseCdc.setArchSet(dujson.getString("ARCH_SET"));
        mirrorPhaseCdc.setHardcSet(dujson.getString("HARDC_SET"));
        mirrorPhaseCdc.setLandsSet(dujson.getString("LANDS_SET"));
        mirrorPhaseCdc.setTaxTyp(dujson.getString("TAX_TYP"));
        int num;
        if(0==type){//新增
            num= mirrorPhaseCOneService.insert(mirrorPhaseCdc);
        }else if(1==type) {//修改
            mirrorPhaseCdc.setId(id);
            num= mirrorPhaseCOneService.update(mirrorPhaseCdc);
        }else{//删除
            mirrorPhaseCdc.setDeFlg(deFlg);
            num= mirrorPhaseCOneService.update(mirrorPhaseCdc);
        }
        return  num;
    }

    /**
     * 新增C2 分期数据
     * @param dujson
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCTwo(JSONObject dujson,int type,int id,String deFlg) throws ParseException {

        DateFormat dateFormatter = DateFormat.getDateTimeInstance();

        MirrorPhaseCTwo mirrorPhaseCTwo=new MirrorPhaseCTwo();
        mirrorPhaseCTwo.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCTwo.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCTwo.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCTwo.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCTwo.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCTwo.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCTwo.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCTwo.setPhName(dujson.getString("PH_NAME"));
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
        mirrorPhaseCTwo.setCrDate(dateFormatter.parse(dujson.getString("CR_DATE")));
        mirrorPhaseCTwo.setChDate(dateFormatter.parse(dujson.getString("CH_DATE")));
        mirrorPhaseCTwo.setPrId(dujson.getString("PR_ID"));
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
        mirrorPhaseCTwo.setPhOpenD(dateFormatter.parse(dujson.getString("PH_OPEN_D")));
        mirrorPhaseCTwo.setPhLandcl(dujson.getString("PH_LANDCL"));
        mirrorPhaseCTwo.setTaxTyp(dujson.getString("TAX_TYP"));
        mirrorPhaseCTwo.setPhMeFlg(dujson.getString("PH_ME_FLG"));
        mirrorPhaseCTwo.setPhMcFlg(dujson.getString("PH_MC_FLG"));
        mirrorPhaseCTwo.setPhToFlg(dujson.getString("PH_TO_FLG"));
        mirrorPhaseCTwo.setPhMaFlg(dujson.getString("PH_MA_FLG"));

        mirrorPhaseCTwo.setPhDevsta(dujson.getString("PH_DEVSTA"));


        int num;
        if(0==type){//新增
            num= mirrorPhaseCTwoService.insert(mirrorPhaseCTwo);
        }else if(1==type) {//修改
            mirrorPhaseCTwo.setId(id);
            num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
        }else{//删除
            mirrorPhaseCTwo.setDeFlg(deFlg);
            num= mirrorPhaseCTwoService.update(mirrorPhaseCTwo);
        }
        return  num;
    }

    /**
     * 新增C3 分期数据
     * @param dujson
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCThree(JSONObject dujson,int type,int id,String deFlg) throws ParseException {

        DateFormat dateFormatter = DateFormat.getDateTimeInstance();

        MirrorPhaseCThree mirrorPhaseCThree=new MirrorPhaseCThree();
        mirrorPhaseCThree.setPhId(dujson.getString("PH_ID"));
        mirrorPhaseCThree.setSapVer(dujson.getString("SAP_VER"));
        mirrorPhaseCThree.setVerNam(dujson.getString("VER_NAM"));
        mirrorPhaseCThree.setHisCode(dujson.getString("HIS_CODE"));
        mirrorPhaseCThree.setHisIcard(dujson.getString("HIS_ICARD"));
        mirrorPhaseCThree.setHisGuid(dujson.getString("HIS_GUID"));
        mirrorPhaseCThree.setHisFlg(dujson.getString("HIS_FLG"));
        mirrorPhaseCThree.setPhName(dujson.getString("PH_NAME"));
        mirrorPhaseCThree.setApStatus(dujson.getString("AP_STATUS"));
        mirrorPhaseCThree.setDeFlg(dujson.getString("DE_FLG"));
        mirrorPhaseCThree.setPrType(dujson.getString("PR_TYPE"));
        mirrorPhaseCThree.setPrCompan(dujson.getString("PR_COMPAN"));

        mirrorPhaseCThree.setBuId(dujson.getString("BU_ID"));
        mirrorPhaseCThree.setCrDate(dateFormatter.parse(dujson.getString("CR_DATE")));
        mirrorPhaseCThree.setChDate(dateFormatter.parse(dujson.getString("CH_DATE")));
        mirrorPhaseCThree.setPrId(dujson.getString("PR_ID"));
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
        mirrorPhaseCThree.setPhOpenD(dateFormatter.parse(dujson.getString("PH_OPEN_D")));
        mirrorPhaseCThree.setPrBugets(dujson.getString("PR_BUGETS"));

        mirrorPhaseCThree.setPhRnlev(dujson.getString("PH_RNLEV"));
        mirrorPhaseCThree.setPhFlplan(dujson.getString("PH_FLPLAN"));

        mirrorPhaseCThree.setPhLeaper(Integer.parseInt(dujson.getString("PH_LEAPER")));
        mirrorPhaseCThree.setPhRenSd(dateFormatter.parse(dujson.getString("PH_REN_SD")));
        mirrorPhaseCThree.setPhRenEd(dateFormatter.parse(dujson.getString("PH_REN_ED")));
        mirrorPhaseCThree.setPhShpadr(dujson.getString("PH_SHPADR"));
        mirrorPhaseCThree.setPhOpname(dujson.getString("PH_OPNAME"));
        mirrorPhaseCThree.setPhOpid(dujson.getString("PH_OPID"));
        mirrorPhaseCThree.setConstype(dujson.getString("CONSTYPE"));
        mirrorPhaseCThree.setPhShFlg(dujson.getString("PH_SH_FLG"));

        int num;
        if(0==type){//新增
            num= mirrorPhaseCThreeService.insert(mirrorPhaseCThree);
        }else if(1==type) {//修改
            mirrorPhaseCThree.setId(id);
            num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
        }else{//删除
            mirrorPhaseCThree.setDeFlg(deFlg);
            num= mirrorPhaseCThreeService.update(mirrorPhaseCThree);
        }
        return  num;
    }


    /**
     * 新增C4 数据
     * @param dujson
     * @return
     * @throws ParseException
     */
    private int saveOrUpdateMirrorPhaseCFour(JSONObject dujson,int type,int id,String deFlg) throws ParseException {

        DateFormat dateFormatter = DateFormat.getDateTimeInstance();

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
        mirrorPhaseCFour.setCrDate(dateFormatter.parse(dujson.getString("CR_DATE")));
        mirrorPhaseCFour.setChDate(dateFormatter.parse(dujson.getString("CH_DATE")));
        mirrorPhaseCFour.setTakeDate(dujson.getString("TAKE_DATE"));
        mirrorPhaseCFour.setPrType(dujson.getString("PR_TYPE"));
        mirrorPhaseCFour.setServlev(dujson.getString("SERVLEV"));
        mirrorPhaseCFour.setTakeType(dujson.getString("TAKE_TYPE"));
        mirrorPhaseCFour.setDeFlg(dujson.getString("DE_FLG"));

        int num;
        if(0==type){//新增
            num= mirrorPhaseCFourService.insert(mirrorPhaseCFour);
        }else if(1==type) {//修改
            mirrorPhaseCFour.setId(id);
            num= mirrorPhaseCFourService.update(mirrorPhaseCFour);
        }else{//删除
            mirrorPhaseCFour.setDeFlg(deFlg);
            num= mirrorPhaseCFourService.update(mirrorPhaseCFour);
        }
        return  num;

    }


    /**
     * 把成功或者失败信息返回给主数据
     * @param body
     * @param error
     */
    private void executorCallBack(String body, boolean error) {
        RestTemplate restTemplate=new RestTemplate();
        //String userAndPass = "PPSUSER:PPS12345"; //测试环境(10.49)
        String userAndPass = "PPSUSER:$jLVrn^8"; //生产环境(10.37)
        HttpHeaders headers = new HttpHeaders();

        // 待修正
       // headers.add("Authorization", "Basic " +  Base64Utility.encode(userAndPass.getBytes()));
        headers.add("Content-Type","text/plain;charset=UTF-8");
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        System.out.println("body==" + body);
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
     * 新增分期 返回成功或者失败
     * @param num
     * @param stageName
     * @return
     */
    private JSONObject insertResultStatus( int num,String stageName ,JSONObject result){
        if(-1==num) {
            result.put("code", "500");
            result.put("message", stageName + "创建失败");
        }else {
            result.put("code", "200");
            result.put("message", stageName + "创建成功");
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
