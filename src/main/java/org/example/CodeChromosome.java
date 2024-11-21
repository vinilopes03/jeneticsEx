package org.example;

import io.jenetics.AbstractChromosome;
import io.jenetics.util.ISeq;

public class CodeChromosome extends AbstractChromosome<CodeGene> {

    // Constructor accepting an ISeq of CodeGene
    protected CodeChromosome(ISeq<CodeGene> genes) {
        super(genes);
    }

    // Generates a random chromosome
    public static CodeChromosome random(int length) {
        ISeq<CodeGene> genes = ISeq.of(CodeGene::random, length);
        return new CodeChromosome(genes);
    }

    @Override
    public CodeChromosome newInstance() {
        return random(length());
    }

    @Override
    public CodeChromosome newInstance(ISeq<CodeGene> genes) {
        return new CodeChromosome(genes);
    }
}
