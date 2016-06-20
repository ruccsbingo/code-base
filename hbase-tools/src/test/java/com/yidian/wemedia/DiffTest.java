package com.yidian.wemedia;

import org.junit.Ignore;
import org.junit.Test;
import yidian.data.meta.types.StatProtos;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/3/28.
 */
public class DiffTest {

    @Ignore
    @Test
    public void testDiff() throws Exception {
        Diff diff = new Diff();
        Map<String, StatProtos.StatValue> real = diff.getRealtimeStatMap();
        Map<String, StatProtos.StatValue> batch = diff.getBatchStatMap(real);
        diff.diff(real, batch);
    }

    @Ignore
    @Test
    public void testReal() throws Exception{
        Diff diff = new Diff();
        System.out.println(diff.getRealtimeStatMap());
    }
}