/*
 * This file is part of SimpleRenamer.
 *
 * JComplete is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SimpleRenamer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JComplete.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Stratos Kamadanis
 */
package datatypes;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class FileItem implements Serializable{
    private String filePath;
    private String fileName;
    private String ext;

    /**
     *
     * @param filePath root folder,no filename,must contain last /
     * @param fileName must contain extension
     */
    public FileItem(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName.substring(0,fileName.lastIndexOf('.'));
        this.ext=fileName.substring(fileName.lastIndexOf('.')+1);
    }

    public FileItem(File file) {
        this.filePath=file.getParent()+"/";
        this.fileName=file.getName().substring(0,file.getName().lastIndexOf('.'));
        this.ext=file.getName().substring(file.getName().lastIndexOf('.')+1);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }
    public String getExt() {
        return ext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileItem fileItem = (FileItem) o;

        if (!fileName.equals(fileItem.fileName)) return false;
        if (!filePath.equals(fileItem.filePath)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filePath.hashCode();
        result = 31 * result + fileName.hashCode();
        return result;
    }

    /**
     * newName must not contain extension
     * @param newName
     * @return
     */
    public boolean rename(String newName){
        if(newName.equals(fileName)){
            return true;
        }
        File newFile=new File(filePath.concat(newName).concat(getExtWithDot()));
        try {
            FileUtils.moveFile(getFile(), newFile);
            fileName=newName;
        } catch (IOException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * newPath must contain the last /
     * @param newPath
     * @return
     */
    public boolean move(String newPath){
        if(newPath.equals(filePath)){
            return true;
        }
        File newFile=new File(newPath.concat(fileName).concat(getExtWithDot()));
        try {
            FileUtils.moveFile(getFile(), newFile);
            filePath =newPath;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public File getFile(){
        return new File(filePath.concat(fileName).concat(getExtWithDot()));
    }
    public String getFilenameWExt(){
        return fileName.concat(getExtWithDot());
    }
    private String getExtWithDot(){
        return ".".concat(ext);
    }

    @Override
    public String toString() {
        return "datatypes.FileItem{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
    public boolean isVideo(){
        return ext.equals("mp4")||ext.equals("avi")||ext.equals("mkv")||ext.equals("3gp");
    }

}
