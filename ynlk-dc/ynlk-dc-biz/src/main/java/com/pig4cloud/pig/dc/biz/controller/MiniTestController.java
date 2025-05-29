/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.dc.biz.controller;
import java.time.LocalDateTime;

import cn.hutool.http.HttpUtil;
import com.pig4cloud.pig.admin.api.entity.SysLog;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteLogService;
import com.pig4cloud.pig.admin.api.feign.RemoteOpenUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.dc.api.dto.WechatLoginDto2;
import com.pig4cloud.pig.dc.api.dto.WechatMiniCode2SessionDTO;
import com.pig4cloud.pig.dc.api.dto.WechatMiniPhoneDTO;
import com.pig4cloud.pig.dc.api.entity.OscNews;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.mapper.OscNewsMapper;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import com.pig4cloud.pig.dc.biz.service.impl.AuthServiceImpl;
import com.pig4cloud.pig.dc.biz.service.impl.TestServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sun.security.util.SecurityConstants;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@ApiIgnore
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mini/test")
@Api(value = "MiniTestController", tags = "小程序-内部测试接口")
public class MiniTestController {

	private final TestServiceImpl testService;

	//service里面开启了分布式事务
	@ApiOperation(value = "测试seata分布式事务", notes = "测试seata分布式事务")
	@GetMapping("/seate/{id}")
	public R seataTest(@PathVariable Integer id ) {
		return testService.seataTest(id);
	}


	@ApiOperation(value = "导入excel", notes = "导入excel")
	@PostMapping("/poi/import")
	public R poiInsertExcel( @RequestBody WechatLoginDto2 dto) {
		insertOrUpdate();
		return R.ok("");
	}

	public static String EXCEL_PATH_ROOT="";

	public void insertOrUpdate() {

		log.info("============================================================================insertOrUpdate");

		try {



			String url="http://117.50.177.58:9000/ucloud/c4159932c1914b36b0e1691b9dca9a66.xlsx";

			String outFilePath="/tmp/c4159932c1914b36b0e1691b9dca9a66.xlsx";

			//String outFilePath="F://chuck//documents//ynlk//韩国项目//215//1726//57d2e461d1624029b664602876479cb2.xlsx";

			HttpUtil.downloadFile(url, outFilePath);

			XSSFWorkbook book = new XSSFWorkbook(outFilePath);		// 文件所在位置

			XSSFSheet sheet = book.getSheetAt(0);


			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");


			log.info("sheet.getLastRowNum(),{}",sheet.getLastRowNum());

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				//List<Object> list1 = new ArrayList<>();
				XSSFRow row = sheet.getRow(i);
				if (row != null) {

					try {
						String number = row.getCell(0).getStringCellValue();  // 电桩编号

						XSSFCell cell1 = row.getCell(1);
						cell1.setCellType(CellType.STRING);

						XSSFCell cell2 = row.getCell(2);
						cell2.setCellType(CellType.STRING);

						XSSFCell cell3 = row.getCell(3);
						cell3.setCellType(CellType.STRING);

						XSSFCell cell4 = row.getCell(4);
						cell4.setCellType(CellType.STRING);

						XSSFCell cell5 = row.getCell(4);
						cell5.setCellType(CellType.STRING);



						Date time6=row.getCell(6).getDateCellValue();

						Date time7=row.getCell(7).getDateCellValue();

						log.info("row.getCell(6).getDateCellValue(),{}",format.format(time6));
						log.info("row.getCell(7).getDateCellValue(),{}",format.format(time7));


						String positionX = cell1.getStringCellValue();
						String positionY = cell2.getStringCellValue();
						String pileType = cell3.getStringCellValue();
						String model = cell4.getStringCellValue();


						if(TextUtils.isEmpty(number)){
							continue;
						}


					} catch (Exception e) {
						log.error("解析异常:"+e);
					}


				}
			}



		} catch (Exception e) {
			log.error("error:"+e);
		}

	}



}
