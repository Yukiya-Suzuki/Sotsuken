import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/imageGet")
@MultipartConfig(maxFileSize = 1048576)
public class ImageGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final File uploaDir = new File("upload");
       
    public ImageGetServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("utf-8");
		List<String> errMessage = new ArrayList<String>();
		String filename;
		
		//name属性がpictのファイルをPartオブジェクトとして取得
		Part part = request.getPart("pict");
		try {
			filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			//アップロードするフォルダ
			String path=getServletContext().getRealPath("upload");
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
			}
			//書き込み
			String saveFile = path + File.separator + filename;
			part.write(saveFile);
			System.out.println(saveFile);
			request.setAttribute("saveFile", "upload/" + filename);
		//	request.setAttribute("saveFile", saveFile);
			request.getRequestDispatcher("/内容確認画面.jsp").forward(request, response);
			//request.getRequestDispatcher("/OperationAPI").forward(request, response);
			
		} catch(Exception e) {
			errMessage.add("画像形式が正しくありません");
			errMessage.add(e.getMessage());
			e.printStackTrace();
			request.setAttribute("errMessage", errMessage);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
