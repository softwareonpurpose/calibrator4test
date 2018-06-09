/**
 * Copyright 2018 Craig A. Stockton
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
package com.softwareonpurpose.calibrator4test;

/**
 * Manage and provide the level of indentation (2 spaces per level by default)
 */
@SuppressWarnings("WeakerAccess")
public class IndentManager {

    private final static int DEFAULT_LEVEL = 2;
    private final int spacesPerLevel;
    private int indentationLevel;

    private IndentManager(int spacesPerLevel) {
        this.spacesPerLevel = spacesPerLevel;
    }

    /**
     * Return a new instance of IndentManager with default number of spaces per level
     *
     * @return Instance of IndentManger
     */
    @SuppressWarnings("WeakerAccess")
    public static IndentManager construct() {
        return new IndentManager(DEFAULT_LEVEL);
    }

    /**
     * Return a new instance of IndentManager with specified number of spaces per level.
     * If argument is below zero, number of spaces per level will be set to zero.
     *
     * @param spacesPerLevel Number of spaces to indent per level of indentation
     * @return Instance of IndentManager
     */
    @SuppressWarnings("WeakerAccess")
    public static IndentManager construct(int spacesPerLevel) {
        return new IndentManager(spacesPerLevel < 0 ? 0 : spacesPerLevel);
    }

    /**
     * @return Is IndentManager is at the root level
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isAtRootLevel() {
        return indentationLevel == 0;
    }

    /**
     * Return a String value prepended with current indentation
     *
     * @param text The String to be formatted
     * @return Formatted String value
     */
    @SuppressWarnings("WeakerAccess")
    public String format(String text) {
        return (text == null || text.isEmpty()) ? "" : String.format("%s%s", getIndentation(), text);
    }

    /**
     * Increment indentation level by one
     */
    @SuppressWarnings("WeakerAccess")
    public void increment() {
        increment(1);
    }

    /**
     * Increment indentation by a specific number of levels
     *
     * @param numberOfLevels Number of levels to increment the current level
     */
    @SuppressWarnings("WeakerAccess")
    public void increment(int numberOfLevels) {
        indentationLevel += numberOfLevels;
    }

    /**
     * Decrement indentation level by one
     */
    @SuppressWarnings("WeakerAccess")
    public void decrement() {
        if (indentationLevel < 1) {
            indentationLevel = 0;
        } else {
            indentationLevel -= 1;
        }
    }

    private String getIndentation() {
        return new String(new char[spacesPerLevel * indentationLevel]).replace('\0', ' ');
    }
}
