package com.cedex;


import com.cedex.jsystem.ReporterLight;
import com.cedex.os.Linux;
import com.cedex.os.OS;
import com.cedex.os.Windows;



/**
 * Station Utils (for windows ans linux as one) class Not using conn
 * 
 * Last changed: $LastChangedDate: 2012-09-09 10:50:07 +0300
 *
 * @author $Author: romang $
 * @version $Revision: 10512 $
 * 
 */
public class StationUtils implements ReporterLight, GlobalParameters {


	  /**
	   * replace windows file separators to linux ones <br>
	   * like: c:\\user\romang to c:/user/romang
	   * 
	   * @param path
	   *              - path for replace
	   * @return
	   */
	  public static String winFileSeparator2LinuxFileSeparator(String path) {
			return path.replaceAll("\\" + Windows.fileSeparator, Linux.fileSeparator);
	  }

	  /**
	   * replace linux file separators to windows ones <br>
	   * like: to /user/romang \\user\romang
	   * 
	   * @param path
	   *              - path for replace
	   * @return
	   */
	  public static String linuxFileSeparator2WinFileSeparator(String path) {
			return path.replaceAll(Linux.fileSeparator, "\\" + Windows.fileSeparator);
	  }

	  /**
	   * add child file/folder name to parent full path <br>
	   * replacing cases like: patent +"/"+child <br>
	   * 
	   * @param childShortName
	   *              - short name of child file/folder
	   * 
	   * 
	   * @throws Exception
	   */
	  public  static String appendFullChildName(String parentFullPath, String childShortName) throws Exception {
          if (OS.isWin()) {
              return Windows.appendFullChildName(parentFullPath,childShortName);
          } else {
              return Linux.appendFullChildName(parentFullPath,childShortName);
          }
      }

	  /**
	   * function replays parent folder (without using File class) If received /
	   * folder (so return the same /)
	   * 
	   * @param childFileEntity
	   *              - file or folder full path
	   * 
	   * 
	   * @throws Exception
	   */
	  public static String getParentFolder(String childFileEntity) throws Exception {
         if (OS.isWin()) {
             return Windows.getParentFolder(childFileEntity);
         } else {
             return Linux.getParentFolder(childFileEntity);
         }
      }



	  /**
	   * function replays short file name or child folder short name (without
	   * using File class) If received / folder (so return "")
	   * 
	   * @param fullPathName
	   *              - file or folder full path
	   * 
	   * 
	   * @throws Exception
	   */
	  public  static String getShortFileName(String fullPathName) throws Exception {
        if (OS.isWin()) {
            return Windows.getShortFileName(fullPathName);
        } else {
            return Linux.getShortFileName(fullPathName);
        }
    }

	  /**
	   * get main (root) folder of current file/path
	   * 
	   * @param fullPathName
	   * @return
	   * @throws Exception
	   */
//	  public abstract String getMainFolder(String fullPathName) throws Exception;
}
