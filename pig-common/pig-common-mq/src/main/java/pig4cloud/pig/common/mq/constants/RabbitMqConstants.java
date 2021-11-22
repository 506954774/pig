package pig4cloud.pig.common.mq.constants;

/**
 * @author LeiChen
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/4/3014:42
 */
public class RabbitMqConstants {


	/**
	 *  交换机
	 */
    public static final String EXCHANGE_TEST = "EXCHANGE_TEST";

	/**
	 *  队列
	 */
    public static final String QUEUE_TEST = "QUEUE_TEST";

	/**
	 *  topic
	 */
    public static final String TOPIC_TEST = "TOPIC_TEST";









    /**
     * 延时消息,delay极限阈值
     * 2的32次方减1
     * https://blog.csdn.net/dyc87112/article/details/96866878
     */
    public static final Long DELAT_TIME_MAX=4294967295L;

}