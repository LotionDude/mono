package org.smack.mono.common.utilities;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class SetUtilities {
    public static <T> Set<T> copyAndAdd(Set<T> originalSet, T additional) {
        return ImmutableSet.<T>builder().addAll(originalSet).add(additional).build();
    }
}
