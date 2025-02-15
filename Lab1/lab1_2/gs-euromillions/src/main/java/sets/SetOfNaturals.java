package sets;

public class SetOfNaturals extends SetOfNumbers {

    @Override
    public void add(int element) {
        if (element > 0)
            super.add(element);
        else
            throw new IllegalArgumentException("Illegal argument: not a natural number");

    }

    public void add(int[] numbers) {
        for (int number : numbers) {
            this.add(number);
        }
    }

}
