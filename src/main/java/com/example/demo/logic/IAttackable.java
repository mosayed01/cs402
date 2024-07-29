package com.example.demo.logic;

public interface IAttackable<I, O> {
    O attack(I input);
}