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
package com.blogspot.skam94.main.logic;

import com.blogspot.skam94.main.datatypes.FileItem;
import com.blogspot.skam94.main.handlers.FileHandler;

import java.io.File;
import java.util.HashMap;

public class Processor {

    private HashMap<Integer, FileItem> selectedFiles;
    private HashMap<Integer,String> proposedNames;
    private Rename rename;

    public Processor(){
        selectedFiles=new HashMap<>();
        proposedNames=new HashMap<>();
        rename=new Rename("Hotwords",true);
    }
    public Processor(Rename rename){
        selectedFiles=new HashMap<>();
        proposedNames=new HashMap<>();
        this.rename=rename;
    }

    public void addFile(File file){
        if(file.isDirectory()){
            HashMap<Integer, FileItem> fis= FileHandler.getFileItemsMap(file.getPath());
            fis.keySet().stream().forEach(key->selectedFiles.putIfAbsent(key, fis.get(key)));
        }else{
            FileItem fi=new FileItem(file);
            selectedFiles.putIfAbsent(fi.hashCode(),fi);
        }
    }

    public HashMap<Integer, FileItem> getSelectedFiles() {
        return selectedFiles;
    }

    public HashMap<Integer,String> getProposed(){
        return proposedNames;
    }
    public void calculateProposed(){
        proposedNames=rename.getProposedMapFI(selectedFiles);
    }
    public void doRename(){
        proposedNames=rename.getProposedMapFI(selectedFiles);
        selectedFiles.keySet().stream().filter(key ->
                proposedNames.containsKey(key)).filter(key ->
                selectedFiles.get(key).rename(proposedNames.get(key))).forEach(key ->
                proposedNames.put(key, "Renamed"));
    }
    public void clear() {
        selectedFiles=new HashMap<>();
        proposedNames=new HashMap<>();
    }

}
