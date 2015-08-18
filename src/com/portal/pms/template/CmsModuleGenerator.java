 package com.portal.pms.template;
 
 import com.javassf.basic.developer.ModuleGenerator;
 
 public class CmsModuleGenerator
 {
   private static String packName = "com.portal.pms.template";
   private static String fileName = "pms.properties";
 
   public static void main(String[] args) {
     new ModuleGenerator(packName, fileName).generate();
   }
 }


 
 
 