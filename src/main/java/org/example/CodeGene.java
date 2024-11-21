package org.example;

import io.jenetics.Gene;

public class CodeGene implements Gene<String, CodeGene> {

    private final String codeSnippet;

    public CodeGene(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    // Generates a random code snippet
    public static CodeGene random() {
        return new CodeGene(generateRandomCodeSnippet());
    }

    // Generates a random code snippet
    private static String generateRandomCodeSnippet() {
        String[] snippets = {
                "int x = 10;",
                "x += 5;",
                "System.out.println(x);",
                "if (x > 10) { x++; }",
                "for (int i = 0; i < x; i++) { System.out.print(i); }"
        };
        return snippets[(int) (Math.random() * snippets.length)];
    }

    public String getAllele() {
        return codeSnippet;
    }

    @Override
    public String allele() {
        return codeSnippet;
    }

    @Override
    public CodeGene newInstance() {
        return CodeGene.random();
    }

    @Override
    public CodeGene newInstance(String allele) {
        return new CodeGene(allele);
    }

    @Override
    public boolean isValid() {
        return true; // Assume all code snippets are valid
    }
}
