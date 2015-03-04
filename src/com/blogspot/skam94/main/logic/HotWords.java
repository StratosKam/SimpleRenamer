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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class HotWords {
    private HashSet<Integer> words;

    public HotWords(String source,boolean lowercaseOnly){
        words=new HashSet<>();
        try {
            if(lowercaseOnly){
                Files.lines(Paths.get(source)).forEach(string->words.add(string.hashCode()));
            }else{
                Files.lines(Paths.get(source)).forEach(string->words.add(string.toLowerCase().hashCode()));
            }
            words.add(" ".hashCode());
            words.add("".hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean contains(String string){
        return words.contains(string.toLowerCase().hashCode());
    }
}
