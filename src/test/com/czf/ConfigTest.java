package com.czf;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.czf.config.SysConfig;
import com.czf.custom_config.config.UserConfig;
import com.czf.entity.Person;
import com.czf.service.TestService;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {


    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private UserConfig userConfig;

    @Test
   public void testMethod(){
//        使用joda框架计算今天剩余时间
        long standardSeconds = new Duration(new DateTime(), new DateTime().millisOfDay().withMaximumValue()).getStandardSeconds();
        System.out.println(standardSeconds);
    }
    /*
    * 树形节点工具类
     */
    @Test
    public void nodeList(){
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));

        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        System.out.println(treeList);


    }


    @Test
    public void  testCustomConfigLoading(){
        System.out.println(userConfig);
    }

    @Test
    public  void testLambda(){
        List<Integer> list = new ArrayList<Integer>();
        for (Integer i = 0 ;i<=10000;i++) {
            list.add(i);
        }
        List<Integer> collect = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println(collect);

//        lambda表达式实现接口
//        TestService testService = ()->{
//            System.out.println("匿名类构造函数，匿名类实现接口");
//        };
//        testService.test();

//<![CDATA[>=]]>
//        <![CDATA[ ]]>
//        TestService testService = (name,age)-> System.out.println("我叫"+name+"  今年"+age +"岁");
//        testService.test2("蜡笔小新",5);.


        TestService testService = (name,age)->{return "我叫"+name+"  今年"+age +"岁";};
        String str = testService.test3("蜡笔小新", 5);
        System.out.println(str);

    }


    /**
     * 测试集合添加元素
     */
    @Test
    public void testLambda3(){
        Person person = new Person();
        person.setName("张三");
        person.setAge(11);
        person.setId(10);

        Person person2 = new Person();
        person2.setName("李四");
        person2.setAge(13);
        person2.setId(11);

        Person person3 = new Person();
        person3.setName("王五");
        person3.setAge(15);
        person3.setId(12);

        Person person4 = new Person();
        person4.setName("赵六");
        person4.setAge(15);
        person4.setId(84);

        Person person5 = new Person();
        person5.setName("胜7");
        person5.setAge(18);
        person5.setId(59);

        List<Person> peopleList = Lists.newArrayList(person, person2, person3, person4, person5);

        Map<Integer, List<Person>> collect = peopleList.stream().collect(Collectors.groupingBy(Person::getAge));

        Map<String, Integer> collect1 = peopleList.stream().collect(Collectors.toMap(Person::getName, Person::getAge));


        HashMap<String, List<Person>> map = new HashMap<>();
        map.put("pp",peopleList);

        HashMap<Person, String> map2 = new HashMap<>();
        AtomicInteger id= new AtomicInteger();

        peopleList.stream().forEach(p->{
            map2.put(p,"pd："+p.getId());
            p.setId(id.getAndIncrement());
        });


        peopleList.stream().forEach(p->{
            System.out.println(map2.get(p));
        });

        System.out.println(collect);


        HashMap<String, String> map3 = new HashMap<>();
        map3.put("pdd","pd");
        System.out.println(map3.remove("pdd"));

    }


    @Test
    public  void testLambda2(){
        //doubles stream先被映射成int stream，然后又被映射成String类型的对象流：
        Stream.of(1.0,2.0,3.0).mapToInt(Double::intValue)
                .mapToObj(i -> "a"+i)
                .forEach(System.out::println);

        System.out.println("=============================================");
        //filter操作在过滤出一个元素后会立马进入下一步执行，而不是等待将整个集合过滤完再操作。
        Stream.of("d2","a2","b1","b30","c").filter(s->{
            System.out.println("filter: " +s);
            return Boolean.TRUE;
        }).forEach(s-> System.out.println("forEach: " +s));


        System.out.println("=============================================");
        //执行效率与steream执行链顺序的关系#
        Stream.of("b1","d1","c1","a1").map(p->{
            System.out.println("map:"+p);
            return p.toUpperCase();
        }).filter(s->{
            System.out.println("filter: " +s);
            return s.startsWith("A");
        }).forEach(s-> System.out.println("forEach: " +s));

        System.out.println("=============================================");
        //调整上面的filter和map的顺序能大量减少map的执行次数，提升执行效率；


        Stream.of("b1","d1","c1","a1").sorted((s1,s2)->{
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        }).filter(s->{
            System.out.println("filter: " +s);
            return s.startsWith("a");
        }).map(p->{
            System.out.println("map:"+p);
            return p.toUpperCase();
        }).forEach(s-> System.out.println("forEach: " +s));

        System.out.println("=============================================");
        //FlatMap方法可以将一个stream中的每一个元素对象转换为另一个stream中的另一种元素对象，因此可以将stream中的每个对象改造成零，一个或多个。flatMap操作的返回流包含这些改造后的对象。‘
        String[] strings = {"hello", "world"};
        List<String> list =
                Arrays.stream(strings)
                        .map(word -> word.split(""))
                        .flatMap(Arrays::stream)
                        .distinct()
                        .collect(Collectors.toList());
        list.forEach(System.out::print);


        System.out.println("\n=============================================");
        //reduce操作可以将stream中所有元素组合起来得到一个元素
        Person person = new Person();
        person.setName("张三");
        person.setAge(11);

        Person person2 = new Person();
        person2.setName("李四");
        person2.setAge(13);

        Person person3 = new Person();
        person3.setName("王五");
        person3.setAge(15);

        ArrayList<Person> personList = Lists.newArrayList(person, person2, person3);

        personList.stream().reduce((p1,p2)-> p1.getAge()>p2.getAge()?p1:p2).ifPresent(System.out::println);

        Person newPerson = personList.stream().reduce(new Person(1,"李狗蛋", 18), (p1, p2) -> {
            p1.setAge(p1.getAge() + p2.getAge());
            p1.setName(p1.getName() + p2.getName());
            return p1;
        });

        System.out.println(newPerson.getName()+"  ===  "+newPerson.getAge());

        Integer reduce = personList.stream().reduce(0, (sum, p) -> sum += p.getAge(), (sum1, sum2) -> sum1 + sum2);
        System.out.println(reduce);


        //.parallelStream()并行流
        Integer reduce2 = personList.parallelStream().reduce(0, (sum, p) -> {
            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
            return sum += p.getAge();
        }, (sum1, sum2) -> {
            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
           return sum1 + sum2;
        });

        System.out.println("\n=============================================");
        // 并行Stream。为了提高大量输入时的执行效率，stream可以采用并行的放行执行。
        //并行流（Parallel Streams）通过ForkJoinPool.commonPool() 方法获取一个可用的ForkJoinPool。
        //-Djava.util.concurrent.ForkJoinPool.common.parallelism=5 ,通过调整jvm参数来修改初始化线程数
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        System.out.println(forkJoinPool.getParallelism());

    }

    /**
     * 测试拼音库
     */
    @Test
    public void testPngyn(){
        System.out.println(PinyinUtil.getPinyin("你好哈哈哈哈"));

    }


    @Test
    public void addMethodTest(){
    }


    @Test
    public void addMethodTest2(){
    }



}
