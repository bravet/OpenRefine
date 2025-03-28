/*******************************************************************************
 * Copyright (C) 2018, OpenRefine contributors
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package com.google.refine.grel.ast;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

public class LiteralExprTest {

    @Test
    public void intLiteralToString() {
        LiteralExpr expr = new LiteralExpr(42, "42");
        assertEquals("42", expr.toString());
    }

    @Test
    public void columnDependencies() {
        LiteralExpr expr = new LiteralExpr(34, "34");
        assertEquals(expr.getColumnDependencies(Optional.of("column")), Optional.of(Collections.emptySet()));
        assertEquals(expr.renameColumnDependencies(Map.of("foo", "bar")), expr);

        LiteralExpr string = new LiteralExpr("foo", "\"foo\"");
        assertEquals(string.getColumnDependencies(Optional.of("foo")), Optional.of(Collections.emptySet()));
        assertEquals(string.renameColumnDependencies(Map.of("foo", "bar")), string);
    }

    @Test
    public void stringLiteralToString() {
        LiteralExpr expr = new LiteralExpr("string with \"\\backslash\"", "\"string with \\\"\\\\backslash\\\"\"");
        assertEquals("\"string with \\\"\\\\backslash\\\"\"", expr.toString());
    }

    @Test
    public void testPattern() {
        Pattern pattern = Pattern.compile("foo");
        LiteralExpr expr = new LiteralExpr(pattern, "/foo/");
        assertEquals(expr.toString(), "/foo/");
    }
}
