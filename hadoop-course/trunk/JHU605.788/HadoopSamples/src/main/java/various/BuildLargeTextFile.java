package various;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

public class BuildLargeTextFile {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
       if ( args.length != 2){
           System.out.println(BuildLargeTextFile.class.getSimpleName() + " <jdk_path> <path>");
           System.exit(-1);
       }
       
       File jdkPath = new File(args[0]);
       Validate.isTrue(jdkPath.exists());
       Validate.isTrue(jdkPath.isDirectory());
       
       File fileToCreate = new File(args[1]);
       Validate.isTrue(!fileToCreate.exists());
       
       FileOutputStream outStream = new FileOutputStream(fileToCreate);
       writeAll(jdkPath, outStream);
       outStream.close();

    }

    private static void writeAll(File jdkPath, OutputStream outStream) throws IOException {
        if (jdkPath.isDirectory()){
            for (File f : jdkPath.listFiles()){
                writeAll(f, outStream);
            }
        } else {
            System.out.println("Writing file [" + jdkPath + "]");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(jdkPath));
            IOUtils.copy(in, outStream);
            in.close();
        }
        
    }

}
