package com.pig4cloud.pig.dc.biz;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.dc.biz.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@SpringBootTest
class PigDcBizApplicationTests {

/*	@Test
	void contextLoads() {



		//encrypted:u30EtXFWP0RuQy7ufM6e7jfRdYXR6MxmOesNOfNyfiAyvl8x1L6XIPih08bR8tD9Bf/7eEcjwRfwE+opijUGP9zBajcsnrjPSawCdO0H2or8pD7B/PSTkwdxeDQ4BRGVlvugrYp8yNEVPZAKWm7x1JVlhYPZZbdVo+CVIB6cv91lMClq/COV8nxaBgshIousTeO8Pzi1iWZZX/mTr2Rh+g==,
		// session_key:Fp7tjQ 4YqzkHV7d0EYX w==,
		// iv:dTXRfycBTfdwad5u3rIRAA==
		//2022-02-09 15:39:30,496 [XNIO-1 task-3] ERROR [com.pig4cloud.pig.dc.biz.utils.WxUtils] WxUtils.java:53 - 解密失败:Key length not 128/192/256 bits.


		var json  = WxUtils.wxDecrypt("u30EtXFWP0RuQy7ufM6e7jfRdYXR6MxmOesNOfNyfiAyvl8x1L6XIPih08bR8tD9Bf/7eEcjwRfwE+opijUGP9zBajcsnrjPSawCdO0H2or8pD7B/PSTkwdxeDQ4BRGVlvugrYp8yNEVPZAKWm7x1JVlhYPZZbdVo+CVIB6cv91lMClq/COV8nxaBgshIousTeO8Pzi1iWZZX/mTr2Rh+g==",
				"Fp7tjQ 4YqzkHV7d0EYX w==",
				"dTXRfycBTfdwad5u3rIRAA==");

		System.out.println(json);

	}*/


	/*@Test
	public void insertOrUpdate() {


		log.info("============================================================================insertOrUpdate");

		try {

			//List<List<Object>> list = new ArrayList<List<Object>>();             // 读取的数据放入该集合中

			XSSFWorkbook book = new XSSFWorkbook("F://chuck//documents//ynlk//韩国项目//215//1726//test.xlsx");		// 文件所在位置

			String url="http://117.50.177.58:9000/ucloud/57d2e461d1624029b664602876479cb2.xlsx";
			String outFilePath="F://chuck//documents//ynlk//韩国项目//215//1726//57d2e461d1624029b664602876479cb2.xlsx";

			HttpUtil.downloadFile(url, outFilePath);
			//XSSFWorkbook book = new XSSFWorkbook(outFilePath);		// 文件所在位置
			XSSFSheet sheet = book.getSheetAt(0);


			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");

			int count =1;

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

			log.info("=================================更新/插入,总数,count:{}",count);


		} catch (Exception e) {
			log.error("error:"+e);
		}

	}*/

}
