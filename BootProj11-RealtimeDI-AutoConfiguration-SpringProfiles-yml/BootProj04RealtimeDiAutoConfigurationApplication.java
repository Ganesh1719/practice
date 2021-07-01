package com.nt;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.nt.controller.MainController;
import com.nt.vo.EmployeeVO;

@SpringBootApplication
public class BootProj04RealtimeDiAutoConfigurationApplication {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Desgs count::");
		int count=sc.nextInt();
		String desgs[]=null;
		if(count>=1)
			desgs=new String[count];
		else {
			System.out.println("invalid desg count  ");
			return;
		}
		for(int i=0;i<count;++i) {
			System.out.println("enter desg"+(i+1));
			String desg=sc.next();
			desgs[i]=desg;
		}
		ApplicationContext ctx=SpringApplication.run(BootProj04RealtimeDiAutoConfigurationApplication.class, args);
		MainController controller=ctx.getBean("controller",MainController.class);
		//invoke methods
		try {
			List<EmployeeVO> listVO=controller.showEmpsByDesgs(desgs);
			System.out.println("Emp details having "+Arrays.toString(desgs));
			listVO.forEach(vo->{
				System.out.println(vo);
			});
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Some Internal problem::"+e.getMessage());
		}
		((ConfigurableApplicationContext) ctx).close();
	}

}
