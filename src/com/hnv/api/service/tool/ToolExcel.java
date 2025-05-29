package com.hnv.api.service.tool;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;

public class ToolExcel {
	public static JSONArray reqData (String fPathScr, int sheetIndex, int rowBegin) throws Exception{
		try {
			// Step 1: Create a FileInputStream object for the Excel file
			FileInputStream 	excelFile 	= new FileInputStream	(fPathScr);

			// Step 2: Get the workbook object for XLSX file
			Workbook 	workbook 	=  WorkbookFactory.create(new File(fPathScr));;
//			if (fPathScr.indexOf("xlsx")>0)
//				workbook 	= new XSSFWorkbook(excelFile);
//			else
//				workbook 	= new HSSFWorkbook(excelFile);
//			
			Sheet 		sheet 		= workbook.getSheetAt(sheetIndex); // Assuming you want the first sheet

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			//Khóa học	Khoa	Lớp	Mã sinh viên	Họ lót	Tên	Ngày sinh	Giới tính	Nơi sinh	Email trường cấp	Email cá nhân S.Viên	
			//Điện thoại di dộng	Khi cần báo tin cho	Quốc tịch SViên	Dân tộc	Tôn giáo	svTamNgung	KY_TAM_NGUNG	KY_QUAY_LAI	SoQD_TAM_NGUNG	
			//LY_DO_TAM_NGUNG	Hộ khẩu thường trú	Địa chỉ hộ khẩu thường trú	Mã Tỉnh	Tỉnh/TP hộ khẩu thường trú	Mã Huyện	Quận/Huyện hộ khẩu thường trú	
			//Mã Xã	Xã/Phường hộ khẩu thường trú	Thôn/Xóm hộ khẩu thường trú	Địa chỉ tạm trú tạm vắng	Tỉnh/TP tạm trú tạm vắng	Quận/Huyện tạm trú tạm vắng	Xã/Phường tạm trú tạm vắng	Thôn/Xóm tạm trú tạm vắng	
			//Địa chỉ gửi giấy báo	Tỉnh/TP gửi giấy báo	Quận/Huyện gửi giấy báo	Xã/Phường gửi giấy báo	Thôn/Xóm gửi giấy báo	Mã số thẻ BHYT	Nơi ĐKý BHYT	
			//Mã khám chữa bệnh BHYT	Giá trị sử dụng BHYT	Số CCCD	Ngày cấp	Tháng cấp	Năm cấp	Năm tốt nghiệp	Ngày ký bằng tốt nghiệp	Người ký bằng tốt nghiệp	
			//Số hiệu bằng tốt nghiệp	Đã kiểm tra bằng chính	Chưa kiểm tra bằng chính	Giấy chứng nhận	Xác minh bằng tốt nghiệp	Miễn giãm học phí	Họ tên Cha	
			//Công việc Cha	Hộ khẩu Cha	Điện thoại Cha	Email Cha	Học vấn Cha	Họ tên Mẹ	Công việc Mẹ	Hộ khẩu Mẹ	Điện thoại Mẹ	Email Mẹ	Học vấn Mẹ	Đối tượng SV	
			//Ghi chú	Ghi chú SViên đã hoàn thành các học phần trong CT đào tạo	CC Quân sự	Số hiệu QS	Nơi cấp Qs	Ngày cấp1	Xếp loại	Mồ côi	
			//MA_SINH_VIEN	KHOA_HOC	LOP	HO_LOT	TEN	NGAY_SINH	SOBD	NAM_TRUNG_TUYEN	NGAY_NHAP_HOC	NHOM_TRUNG_TUYEN	MA_PHUONG_THUC_XT	TEN_PHUONG_THUC_XT	
			//MA_NGANH_TT	TEN_NGANH_TT	DAO_TAO_TU_NAM	DAO_TAO_DEN_NAM	NAM_HET_HAN_DT	MA_TRUONG_12	TEN_TRUONG_12	DIA_CHI_TRUONG_12	MA_TINH_TRUONG_12	MA_QUAN_TRUONG_12	MA_XA_TRUONG_12	DIEM_XET_TUYEN	DIEM_TRUNG_TUYEN	NAM_TOT_NGHIEP_12	HOC_LUC_12	HANH_KIEM_12	NAM_TS_CT1	NAM_TRUNG_TUYEN_CT1	SoQDTrungTuyen_CT_1	NGAY_KY_QD_TRUNG_TUYEN_CT1	NAM_TS_CT2	SoQDTrungTuyen_CT_2	NGAY_KY_QD_TRUNG_TUYEN_CT2	TEN_TRUONG_CHUYEN_DEN	SoQD_CHUYEN_DEN_TRUONG	NGAY_CHUYEN_DEN_TRUONG	TEN_TRUONG_CHUYEN_DI	SoQD_CHUYEN_DI_TRUONG	NGAY_CHUYEN_DI_TRUONG	KHOI_NGANH	DOI_TUONG_TS	TRINH_DO_DAO_TAO	HINH_THUC_DAO_TAO	HINH_THUC_HOC	CT_DAO_TAO	MA_CT_DAO_TAO	NGON_NGU_DAO_TAO	LOAI_HINH_DAO_TAO	MA_NGANH_DAO_TAO	TEN_NGANH_DAO_TAO	TEN_CHUYEN_NGANH_DAO_TAO	SoQD_MO_NGANH	NgayBH_QD_MO_NGANH	SoQD_CHO_PHEP_DAO_TAO	NgayBH_QD_CHO_PHEP_DAO_TAO	THOI_GIAN_TOT_NGHIEP	SoQD_CONG_NHAN_TOT_NGHIEP	NGAY_KY_QD_TN	SO_HIEU_VAN_BANG	SO_VAO_SO_TN	LOAI_TOT_NGHIEP

			JSONArray lst = new JSONArray();
			// Step 3: Loop through rows and columns, writing to CSV
			for (Row row : sheet) {
				if (rowBegin>0) {
					rowBegin = rowBegin-1;
					continue;
				}
				JSONObject data = new JSONObject();
				int	index		= -1;
				for (Cell cell : row) {		
					index++;
					switch (cell.getCellTypeEnum()) {
					
					case STRING: 
						data.put(index, cell.getStringCellValue());
						break;

					case FORMULA: 
						if 		(cell.getCachedFormulaResultTypeEnum() == CellType.NUMERIC && cell.getNumericCellValue() != 0) 	data.put(index, cell.getNumericCellValue()) ;
						else if (cell.getCachedFormulaResultTypeEnum() == CellType.STRING) 										data.put(index, cell.getStringCellValue()) ;
						else if (cell.getCachedFormulaResultTypeEnum() == CellType.FORMULA) 									data.put(index, cell.getStringCellValue());
						else data.put(index, null);
						break;

					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) data.put(index, dateFormat.format(cell.getDateCellValue()));
						else data.put(index, cell.getNumericCellValue());
						break;

					case BOOLEAN: 
						data.put(index, cell.getBooleanCellValue());
						break;

					default:	
						data.put(index, null);
						break;

					}
				}
				
				lst.add(data);
			}

			// Close the workbook
			workbook.close();

			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static void main(String []a) throws Exception{
		String path = "C:\\tmp\\Hồ sơ SV_50K.xls";
		JSONArray lst = reqData (path, 0, 1);
		System.out.println(lst);
	}
}
