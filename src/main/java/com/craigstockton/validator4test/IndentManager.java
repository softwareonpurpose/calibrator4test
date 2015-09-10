/**
 * Copyright 2015 Craig A. Stockton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craigstockton.validator4test;

/**
 * Used to manage the level of indentation (4 spaces per level), with clients incrementing/decrementing the level
 */
public class IndentManager {

    private final int spacesPerLevel;
    private int indentationLevel;

    private IndentManager(int spacesPerLevel) {
        this.spacesPerLevel = spacesPerLevel;
    }

    /**
     * Return a new instance of IndentManager
     *
     * @return Instance of IndentManger
     */
    public static IndentManager getInstance() {
        return new IndentManager(2);
    }

    /**
     * Return a new instance of IndentManager
     *
     * @param spacesPerLevel Number of spaces to indent per level of indentation
     * @return Instance of IndentManager
     */
    public static IndentManager getInstance(int spacesPerLevel) {
        return new IndentManager(spacesPerLevel);
    }

    /**
     * Increment indentation level by one
     */
    public void increment() {
        increment(1);
    }

    /**
     * Increment indentation by a specific number of levels
     *
     * @param numberOfLevels Number of levels to increment the current level
     */
    public void increment(int numberOfLevels) {
        indentationLevel += numberOfLevels;
    }

    /**
     * Decrement indentation level by one
     */
    public void decrement() {
        if (indentationLevel > 0)
            indentationLevel--;
    }

    /**
     * Format a line, padded with current indentation
     *
     * @param line The line of text to be formatted
     * @return
     */
    public String format(String line) {
        boolean lineIsNullOrEmpty = line == null || line.length() == 0;
        return lineIsNullOrEmpty ? "" : String.format("%s%s", getIndentation(), line);
    }

    private String getIndentation() {
        return new String(new char[spacesPerLevel * indentationLevel]).replace('\0', ' ');
    }

    public boolean isAtRootLevel() {
        return indentationLevel == 0;
    }
}
