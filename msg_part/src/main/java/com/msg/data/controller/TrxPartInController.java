package com.msg.data.controller;


import com.msg.data.model.ErrCode;
import com.msg.data.model.TrxPartInModel;
import com.msg.data.service.TrxPartInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by yoga.wiguna on 09/07/2018.
 */

@RestController
@RequestMapping("/stock-in")
public class TrxPartInController {

    @Autowired
    private TrxPartInService trxPartInService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> createCluster(@RequestBody TrxPartInModel partInModel, UriComponentsBuilder uriComponentsBuilder) {
        if (trxPartInService.isPartsInExist(partInModel.getId_parts_number()) == true) {
            trxPartInService.insertPartsStock(partInModel);
            return new ResponseEntity(new ErrCode("201", "Data Stock berhasil Disimpan"), HttpStatus.CREATED);
        }
            return  new ResponseEntity(new ErrCode("409", "Data Stock sudah ada"), HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<TrxPartInModel>> getDataPartsIn(){
        List<TrxPartInModel> list = trxPartInService.getPartsIn();
        return new ResponseEntity<List<TrxPartInModel>>(list, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<TrxPartInModel> updateUser(@RequestBody TrxPartInModel trxPartInModel) {
        trxPartInService.updateTrxPart(trxPartInModel);
        return new ResponseEntity(new ErrCode("201", "Data Stock berhasil diubah"), HttpStatus.OK);
    }

    @DeleteMapping("/delete={partnumber}")
    public ResponseEntity<Void> deletePartNumber(@PathVariable("partnumber") String trx_parts_stock_in) {
        trxPartInService.deleteId(trx_parts_stock_in);
        return new ResponseEntity(new ErrCode("201", "Data Stock berhasil Dihapus"), HttpStatus.OK);
    }

}
