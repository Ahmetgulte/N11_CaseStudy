package com.example.casestudyn11.base

abstract class Mapper<I,O> {
    abstract fun map(from: I): O
}