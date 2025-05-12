package com.hulzzuk.review.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hulzzuk.location.model.enumeration.LocationEnum;
import com.hulzzuk.review.model.service.ReviewService;
import com.hulzzuk.review.model.vo.ReviewVO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	  // 마이 리뷰 조회
	  @RequestMapping("select.do")
	  public ModelAndView getMyReviewList(ModelAndView mv, 
			  							@RequestParam(name = "userId") String userId) {
		  return reviewService.getMyReviewList(mv,userId);
	  }
		
	  // 리뷰 생성 => 생성 페이지로 이동
	  @RequestMapping("moveCreate.do")
	  public ModelAndView moveCreateReview(ModelAndView mv, @RequestParam("locationEnum") LocationEnum locationEnum, @RequestParam(name = "locId") String locId) {
		  mv.addObject("locationEnum", locationEnum);
		  mv.addObject("locId", locId);
		  
		  mv.setViewName("review/createReview");
	        
	        return mv;
	  }
	  
	  // 리뷰 생성 => insert 문 실행
	  @RequestMapping("create.do")
	  public ModelAndView createReview(ModelAndView mv, @RequestParam("locationEnum") LocationEnum locationEnum,
			  HttpServletRequest request,
              @RequestParam("locId") String locId,
              ReviewVO reviewVO) {

		// locId와 enum을 서비스에 그대로 전달
		return reviewService.createReview(mv,locationEnum, request, locId, reviewVO);
	  }
	  
	  // 리뷰 생성 => 생성 확인 팝업페이지 연결
	  @RequestMapping("moveCreatePopUp.do")
	  public ModelAndView moveCreatePopUp(ModelAndView mv, HttpServletRequest request,
											@RequestParam(name = "reviewId") Long reviewId,
											@RequestParam(name = "message") String message,
											@RequestParam(name = "width") int width,
											@RequestParam(name = "height") int height) {
			
			mv.addObject("reviewId", reviewId);
			mv.addObject("message", message);
			mv.addObject("actionUrl", request.getContextPath() + "/review/create.do?reviewIds="+ reviewId);
			mv.addObject("width", width);
			mv.addObject("height", height);
			
			mv.setViewName("common/popUp");
			
			return mv;
	  }
	  
	  // 리뷰 생성 => summerNote 이미지파일 경로 지정
	  @RequestMapping("imagePath.do")
	  public ModelAndView imagePath(ModelAndView mv) {
		  return mv;
	  }
	  
//	  @RequestMapping("create.do")
//	  @PostMapping(value = "/image-upload")
//	  public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
//			try {
//				// 서버에 저장할 경로
//				String uploadDirectory = context.getServletContext().getRealPath("/resources/images/review"); 
//				
//				// 업로드 된 파일의 이름
//				String originalFileName = file.getOriginalFilename();
//				
//				// 업로드 된 파일의 확장자
//				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//				
//				// 업로드 될 파일의 이름 재설정 (중복 방지를 위해 UUID 사용)
//				String uuidFileName = UUID.randomUUID().toString() + fileExtension;
//				
//				// 위에서 설정한 서버 경로에 이미지 저장
//				file.transferTo(new File(uploadDirectory, uuidFileName));
//			
//				System.out.println("************************ 업로드 컨트롤러 실행 ************************");
//				System.out.println(uploadDirectory);
//				
//				// Ajax에서 업로드 된 파일의 이름을 응답 받을 수 있도록 해줍니다.
//				return ResponseEntity.ok(uuidFileName);
//			} catch (Exception e) {
//				return ResponseEntity.badRequest().body("이미지 업로드 실패");
//			}
//			
//		}
//	  
	  
	// 리뷰 삭제
		@RequestMapping("delete.do")
	    @ResponseBody
	    public String deleteReview(@RequestParam(name = "reviewIds") String reviewIds) {
	        // 삭제 후 목록 페이지로 리다이렉트
			reviewService.deleteReview(reviewIds);
	        return "success";
	    }
	
	// 리뷰 삭제 => 팝업 페이지 연결
	@RequestMapping("moveDelete.do")
	public ModelAndView moveDeleteReview(ModelAndView mv, HttpServletRequest request,
										@RequestParam(name = "reviewIds") String reviewIds,
										@RequestParam(name = "message") String message,
                                       @RequestParam(name = "width") int width,
                                       @RequestParam(name = "height") int height) {
		
		// URL 인코딩
        String encodedReviewIds = URLEncoder.encode(reviewIds, StandardCharsets.UTF_8);
		

		mv.addObject("reviewIds", reviewIds);
		mv.addObject("message", message);
        mv.addObject("actionUrl", request.getContextPath() + "/review/delete.do?reviewIds="+ encodedReviewIds);
        mv.addObject("width", width);
        mv.addObject("height", height);
		
        mv.setViewName("common/popUp");
        
        return mv;
	}
}
