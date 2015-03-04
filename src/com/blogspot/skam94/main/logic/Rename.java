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

import java.util.ArrayList;
import java.util.HashMap;

public class Rename {
    private HotWords hotwords;

    public Rename(String hotWordsPath,boolean lowercase){
        hotwords=new HotWords(hotWordsPath,lowercase);
    }

    public String getProposedName(String oldname){
        String proposed="";
        String[] tokens=oldname.replaceAll("\\."," ").split(" ");
        for(String string:tokens){
            if(!hotwords.contains(string)){
                proposed=proposed.concat(capitalizeFirst(string)).concat(" ");
            }
        }
        proposed=proposed.replaceAll("\\(","");
        proposed=proposed.replaceAll("\\)","");
        proposed=proposed.trim();
        return proposed;
    }

    private String capitalizeFirst(String string){
        string=string.trim();
        string=string.toLowerCase();
        if(Character.isAlphabetic(string.charAt(0))){
//            System.out.println(string);
            return string.substring(0,1).toUpperCase()+string.substring(1);
        }else{
            return string;
        }
    }
    public ArrayList<String> getProposed(ArrayList<String> strings){
        ArrayList<String> proposed=new ArrayList<>();
        strings.stream().forEach(string->proposed.add(getProposedName(string)));
        return proposed;
    }
    public ArrayList<String> getProposedFI(ArrayList<FileItem> fileItems){
        ArrayList<String> proposed=new ArrayList<>();
        fileItems.stream().forEach(fileItem->proposed.add(getProposedName(fileItem.getFileName())));
        return proposed;
    }
    public HashMap<Integer,String> getProposedMap( HashMap<Integer,String> strings){
        HashMap<Integer,String> proposed=new HashMap<>();
        strings.keySet().forEach(key->proposed.put(key,getProposedName(strings.get(key))));
        return proposed;
    }
    public HashMap<Integer,String> getProposedMapFI( HashMap<Integer, FileItem> strings){
        HashMap<Integer,String> proposed=new HashMap<>();
        strings.keySet().forEach(key->proposed.put(key,getProposedName(strings.get(key).getFileName())));
        return proposed;
    }
}
