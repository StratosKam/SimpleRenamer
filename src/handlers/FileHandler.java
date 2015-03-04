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
package handlers;

import datatypes.FileItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {
    public static ArrayList<FileItem> getFileItems(String path){
        ArrayList<FileItem> files=new ArrayList<>();
        try {
            Files.walk(Paths.get(path)).forEach(filePath->{
                if(Files.isRegularFile(filePath)){
                    files.add(new FileItem(filePath.getParent().toString().concat("/"),filePath.getFileName().toString()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static HashMap<Integer, FileItem> getFileItemsMap(String path){
        HashMap<Integer, FileItem> files=new HashMap<>();
        try {
            Files.walk(Paths.get(path)).forEach(filePath->{
                if(Files.isRegularFile(filePath)){
                    FileItem fi=new FileItem(filePath.getParent().toString().concat("/"),filePath.getFileName().toString());
                    files.put(fi.hashCode(),fi);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

}
