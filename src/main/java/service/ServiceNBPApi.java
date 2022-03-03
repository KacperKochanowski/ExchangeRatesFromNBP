package service;

import nbpApi.Rate;
import nbpApi.Table;
import repository.RateRepository;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ServiceNBPApi implements ServiceNBP{
    private final RateRepository rates;

    public ServiceNBPApi(RateRepository rates) {
        this.rates = rates;
    }

    @Override
    public List<String> findAllCodes(Table table) throws IOException, InterruptedException {
        return this.rates.findByTableAndDate(Table.TABLE_A, null)
                .stream()
                .map(Rate::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public double calc(double amount, String source, String target) throws IOException, InterruptedException {
        final List<Rate> rates = this.rates.findByTableLast(Table.TABLE_A);
        final Optional<Rate> sourceRate = rates.stream()
                .filter(rate -> rate.getCode().equals(source))
                .findFirst();
        final Optional<Rate> targetRate = rates.stream()
                .filter(rate -> rate.getCode().equals(target))
                .findFirst();
        if(sourceRate.isPresent() && targetRate.isPresent()){
            return amount * sourceRate.get().getMid() / targetRate.get().getMid();
        } else {
            throw new InvalidParameterException("Nieznane kody walut!!");
        }
    }

    @Override
    public List<Rate> findAll(Table table, LocalDate date) throws IOException, InterruptedException {
        return rates.findByTableAndDate(table, date);
    }

    @Override
    public List<Rate> findAll(Table table) throws IOException, InterruptedException {
        return rates.findByTableLast(table);
    }
}