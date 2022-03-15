package br.gov.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

public class GenericService {
    
    protected void verificaParametro(Long id) {
        if(Objects.isNull(id)){
            throw new BadRequestException();
        }
    }

    protected void verificaParametro(String str) throws BadRequestException {
        if(Objects.isNull(str) || str.isBlank()){
            throw new BadRequestException();
        }
    }

    protected void verificaSeObjetoFoiEncontrado(Object object) throws NotFoundException {
        if(Objects.isNull(object)){
            throw new NotFoundException();
        } else {
            List<?> lista = null;
            if (object.getClass().isArray()) {
                lista = Arrays.asList((Object[])object);
            } else if (object instanceof Collection) {
                lista = new ArrayList<>((Collection<?>)object);
            }
            if(!Objects.isNull(lista) && lista.isEmpty()){
                throw new NotFoundException();
            }
        }
    }

}
