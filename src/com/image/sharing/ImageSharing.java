/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.image.sharing;

import com.algorithm.knsharing.KNNSharing;
import com.util.Constants;
import com.util.ImgDiffPercent;
import com.util.SendMailBySite;

import java.io.File;

/**
 *
 * @author opulent
 */
public class ImageSharing {

	public ImageSharing() {

	}

	public void CreateShares(String baseFileDirectory, String filePath, int userid) {
		// try {
		// CREATING SHAREs
		KNNSharing x = new KNNSharing(baseFileDirectory, filePath, userid);

	}

	public int GenerateShare(String baseFileDirectory, String emailId, long loginId) {

		try {
			String msg = "Your account Credential Share is : ";

			// make Watermarked image and Sende it onto the email
			File sourceImageFile = new File(baseFileDirectory + File.separator + "knshares2_" + loginId + ".jpg");
			File destImageFile = new File(baseFileDirectory + File.separator + "share2_on_mail" + loginId + ".jpg");
			ImgDiffPercent.addTextWatermark(ImgDiffPercent.getId("user"), sourceImageFile, destImageFile);

			String result = SendMailBySite.sendMail(emailId, Constants.MAILMESSAGE, msg,
					baseFileDirectory + File.separator + "share2_on_mail" + loginId + ".jpg");
			if (result.equals("Message Sending Failed....")) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
