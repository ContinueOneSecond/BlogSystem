package utils;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mywebproject.model.UserFiles;

public class UpLoad {
	public Map<String, Object> upLoafImage(MultipartFile file, String REALPATH) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		;

		String fileName = file.getOriginalFilename();
			
		System.out.print("文件的名字是"+fileName);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));

		System.out.println(suffix);
		if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".gif") || suffix.equals(".png")
				|| suffix.equals(".bmp") || suffix.equals(".webp")) {
			// 保存
			try {

				byte[] bytes = file.getBytes();

				Path path = Paths.get(REALPATH + fileName);

				Files.write(path, bytes);
				resultmap.put("success", 1);
				resultmap.put("massage", "上传成功");
				resultmap.put("url", "/index/images/" + fileName);

				System.out.println("执行upload方法");

			} catch (Exception e) {
				resultmap.put("success", 0);
				resultmap.put("message", "上传失败！");
				e.printStackTrace();
			}

			return resultmap;

		} else {
			resultmap.put("success", 0);
			resultmap.put("message", "选择的图片格式有误！");
			return resultmap;
		}

	}

	public UserFiles upLoaFile(MultipartFile file, String fileName, String REALPATH) throws IOException {

		// 获得上传文件的名称
		String oldFileName = file.getOriginalFilename();
		// 获得上传名称的后缀名
		String oldsuffix = oldFileName.substring(oldFileName.lastIndexOf("."));
		// 获得新名称的后缀名
		String newFileName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if (newFileName.equals(fileName)) {
			fileName = fileName + oldsuffix;
		}
		
		// 保存
		try {
			//设置编码格式只能解决文本问题，而压缩或者图片格式全部会破坏
			//Charset charset = Charset.forName("Unicode");

			
			//获得缓冲区 
			ByteBuffer bytBuf = ByteBuffer.allocate(1024);
			
			
			FileInputStream in = (FileInputStream) file.getInputStream();
			FileOutputStream out = new FileOutputStream(REALPATH + fileName);
			
			//IO流获取通道
			FileChannel fileIn = in.getChannel();
			FileChannel fileOut = out.getChannel();

			//往缓冲区写数据直到limit边界为-1
			while (fileIn.read(bytBuf) != -1) {
				//切换到读取数据模式
				bytBuf.flip();
				
				//如果position 和 limit 中还有数据则进行写数据
				while (bytBuf.hasRemaining()) {

					//CharBuffer charBuffer = charset.decode(bytBuf);
					
					//System.out.println("charBuffer的内容是："+charBuffer.toString());
					
					//charset.encode(charBuffer);
										
					fileOut.write(bytBuf);
				}
				bytBuf.clear();

			}

			out.flush();
			fileIn.close();
			in.close();
			fileOut.close();
			out.close();

//				byte[] bytes = file.getBytes();
//						
//				String content = new String(bytes,"gbk");
//				
//				System.out.println("content"+content.toString());
//				
//				Path path = Paths.get(REALPATH + fileName);
//
//				Files.write(path, bytes);
	
			UserFiles thisFiles = new UserFiles();
			thisFiles.setFile_url("/index/files/" + fileName);
			thisFiles.setFile_name(fileName);
			
			System.out.println("执行uploadfile方法");
			return thisFiles;	

		} catch (Exception e) {
			e.printStackTrace();
		}
			return null;
	}

}
