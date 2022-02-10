package com.pig4cloud.pig.dc.biz;

import com.pig4cloud.pig.dc.biz.utils.WxUtils;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PigDcBizApplicationTests {

	@Test
	void contextLoads() {



		//encrypted:u30EtXFWP0RuQy7ufM6e7jfRdYXR6MxmOesNOfNyfiAyvl8x1L6XIPih08bR8tD9Bf/7eEcjwRfwE+opijUGP9zBajcsnrjPSawCdO0H2or8pD7B/PSTkwdxeDQ4BRGVlvugrYp8yNEVPZAKWm7x1JVlhYPZZbdVo+CVIB6cv91lMClq/COV8nxaBgshIousTeO8Pzi1iWZZX/mTr2Rh+g==,
		// session_key:Fp7tjQ 4YqzkHV7d0EYX w==,
		// iv:dTXRfycBTfdwad5u3rIRAA==
		//2022-02-09 15:39:30,496 [XNIO-1 task-3] ERROR [com.pig4cloud.pig.dc.biz.utils.WxUtils] WxUtils.java:53 - 解密失败:Key length not 128/192/256 bits.


		var json  = WxUtils.wxDecrypt("u30EtXFWP0RuQy7ufM6e7jfRdYXR6MxmOesNOfNyfiAyvl8x1L6XIPih08bR8tD9Bf/7eEcjwRfwE+opijUGP9zBajcsnrjPSawCdO0H2or8pD7B/PSTkwdxeDQ4BRGVlvugrYp8yNEVPZAKWm7x1JVlhYPZZbdVo+CVIB6cv91lMClq/COV8nxaBgshIousTeO8Pzi1iWZZX/mTr2Rh+g==",
				"Fp7tjQ 4YqzkHV7d0EYX w==",
				"dTXRfycBTfdwad5u3rIRAA==");

		System.out.println(json);

	}

}
