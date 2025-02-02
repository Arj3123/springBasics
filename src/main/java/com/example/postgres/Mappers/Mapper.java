package com.example.postgres.Mappers;

public interface Mapper <A,B>{
    B mapto(A a);
    A mapfrom(B b);
}
