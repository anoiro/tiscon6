package com.tiscon.service;

import com.tiscon.code.OptionalServiceType;
import com.tiscon.code.PackageType;
import com.tiscon.dao.EstimateDao;
import com.tiscon.domain.Customer;
import com.tiscon.domain.CustomerOptionService;
import com.tiscon.domain.CustomerPackage;
import com.tiscon.dto.UserOrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tiscon.form.UserOrderForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 引越し見積もり機能において業務処理を担当するクラス。
 *
 * @author Oikawa Yumi
 */
@Service
public class EstimateService {

    /** 引越しする距離の1 kmあたりの料金[円] */
    private static final int PRICE_PER_DISTANCE = 100;

    private final EstimateDao estimateDAO;

    /**
     * コンストラクタ。
     *
     * @param estimateDAO EstimateDaoクラス
     */
    public EstimateService(EstimateDao estimateDAO) {
        this.estimateDAO = estimateDAO;
    }

    /**
     * 見積もり依頼をDBに登録する。
     *
     * @param dto 見積もり依頼情報
     */
    @Transactional
    public void registerOrder(UserOrderDto dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        estimateDAO.insertCustomer(customer);

        if (dto.getWashingMachineInstallation()) {
            CustomerOptionService washingMachine = new CustomerOptionService();
            washingMachine.setCustomerId(customer.getCustomerId());
            washingMachine.setServiceId(OptionalServiceType.WASHING_MACHINE.getCode());
            estimateDAO.insertCustomersOptionService(washingMachine);
        }

        List<CustomerPackage> packageList = new ArrayList<>();

        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BOX.getCode(), dto.getBox()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BED.getCode(), dto.getBed()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.BICYCLE.getCode(), dto.getBicycle()));
        packageList.add(new CustomerPackage(customer.getCustomerId(), PackageType.WASHING_MACHINE.getCode(), dto.getWashingMachine()));
        estimateDAO.batchInsertCustomerPackage(packageList);
    }
    //追加

    public Integer boxCheck(UserOrderDto dto) {
        int boxes = getBoxForPackage(dto.getBox(), PackageType.BOX)
                + getBoxForPackage(dto.getBed(), PackageType.BED)
                + getBoxForPackage(dto.getBicycle(), PackageType.BICYCLE)
                + getBoxForPackage(dto.getWashingMachine(), PackageType.WASHING_MACHINE);
        if(boxes <= 200){
            return 0;
        }
        else{
            return 1;
        }
        }

    //
    /**
     * 見積もり依頼に応じた概算見積もりを行う。
     *
     * @param dto 見積もり依頼情報
     * @return 概算見積もり結果の料金
     */
    public double getPrice(UserOrderDto dto ,UserOrderForm userOrderForm) {
        double distance = estimateDAO.getDistance(dto.getOldPrefectureId(), dto.getNewPrefectureId());
        // 小数点以下を切り捨てる
        int distanceInt = (int) Math.floor(distance);

        // 距離当たりの料金を算出する
        double priceForDistance = distanceInt * PRICE_PER_DISTANCE;

        int boxes = getBoxForPackage(dto.getBox(), PackageType.BOX)
                + getBoxForPackage(dto.getBed(), PackageType.BED)
                + getBoxForPackage(dto.getBicycle(), PackageType.BICYCLE)
                + getBoxForPackage(dto.getWashingMachine(), PackageType.WASHING_MACHINE);

        // 箱に応じてトラックの種類が変わり、それに応じて料金が変わるためトラック料金を算出する。
        double pricePerTruck = estimateDAO.getPricePerTruck(boxes);

        // オプションサービスの料金を算出する。
        double priceForOptionalService = 0;

        //追加
        String movingDate = userOrderForm.getMovingDate();
        String country = "japan,america,russia";
        String month = movingDate.substring(5,7);

        System.out.println(month);


        //
        if (dto.getWashingMachineInstallation()) {
            priceForOptionalService = estimateDAO.getPricePerOptionalService(OptionalServiceType.WASHING_MACHINE.getCode());
        }



        if(month.equals("03")  || month.equals("04")){
            return (priceForDistance + pricePerTruck) * 1.5 + priceForOptionalService;
        }
        else if(month.equals("09")){
            return (priceForDistance + pricePerTruck) * 1.2 + priceForOptionalService;
        }
        else  {
            return priceForDistance + pricePerTruck + priceForOptionalService;
        }
    }

    /**
     * 荷物当たりの段ボール数を算出する。
     *
     * @param packageNum 荷物数
     * @param type       荷物の種類
     * @return 段ボール数
     */
    private int getBoxForPackage(int packageNum, PackageType type) {
        return packageNum * estimateDAO.getBoxPerPackage(type.getCode());
    }
}