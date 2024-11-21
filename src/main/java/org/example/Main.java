package org.example;

import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.ISeq;

import javax.tools.*;
import java.io.StringWriter;

public class Main {

    public static void main(String[] args) {
        // Define the chromosome length
        int chromosomeLength = 5;

        // 1. Define the genotype factory
        Genotype<CodeGene> gtf = Genotype.of(CodeChromosome.random(chromosomeLength));

        // 2. Create the evolution engine
        Engine<CodeGene, Integer> engine = Engine
                .builder(Main::eval, gtf) // Fitness function and genotype factory
                .build();

        // 3. Execute evolution and collect the best result
        Genotype<CodeGene> result = engine.stream()
                .limit(100) // Limit evolution to 100 generations
                .collect(EvolutionResult.toBestGenotype());

        // Output the best solution

        System.out.println("Best Generated Code:");
        for (CodeGene gene : result.chromosome()) {
            System.out.println(gene.allele());
        }
    }

    // Fitness function
    private static Integer eval(Genotype<CodeGene> gt) {
        // Combine all genes into a single Java code block
        StringBuilder codeBuilder = new StringBuilder();
        gt.chromosome().forEach(gene -> codeBuilder.append(gene.allele()).append("\n"));
        String code = wrapInClass(codeBuilder.toString());

        // Compile the code and calculate fitness
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StringWriter output = new StringWriter();
        JavaFileObject file = new InMemoryJavaFileObject("GeneratedClass", code);

        boolean compiled = compiler.getTask(output, null, diagnostics, null, null, ISeq.of(file)).call();
        return compiled ? code.length() : -diagnostics.getDiagnostics().size();
    }

    // Adds boilerplate class structure
    private static String wrapInClass(String code) {
        return "public class GeneratedClass {\n" +
                "    public static void main(String[] args) {\n" +
                code +
                "    }\n" +
                "}";
    }
}
