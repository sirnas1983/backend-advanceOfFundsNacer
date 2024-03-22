package com.nacer.reportes.bootstrap;


import com.nacer.reportes.exceptions.ResourceNotFoundException;
import com.nacer.reportes.model.*;
import com.nacer.reportes.repository.efector.EfectorRepository;
import com.nacer.reportes.repository.region.RegionRepository;
import com.nacer.reportes.service.region.RegionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private EfectorRepository efectorRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionRepository regionRepository;

    private static final Map<String, RegionEnum> regionMap = new HashMap<>();

    static {
        regionMap.put("R I", RegionEnum.I);
        regionMap.put("R II", RegionEnum.II);
        regionMap.put("R III", RegionEnum.III);
        regionMap.put("R IV", RegionEnum.IV);
        regionMap.put("R V", RegionEnum.V);
        regionMap.put("R VI", RegionEnum.VI);
        regionMap.put("R VII", RegionEnum.VII);
        regionMap.put("R VIII", RegionEnum.VIII);
        regionMap.put("SUBSEC", RegionEnum.SUBSECRETARIA);
    }
    @Override
    public void run(String... args) throws Exception {
//        cargarRegiones();
//        insertData();
    }

    @Transactional
    private void cargarRegiones(){
        for (RegionEnum regionEnum : RegionEnum.values()) {
            Region region = new Region();
            region.setRegionEnum(regionEnum);
            region.setAuditor(new Auditor());
            region.setNombre(regionEnum.toString());
            regionRepository.save(region);
        }
    }

    @Transactional
    private void insertData() {

        String[] data = {
                "R I,La Clotilde,76,H02856,P.S. A  LA CLOTILDE",
                "R I,Samuhu,70,H04434,P.S. A  SAMUHU",
                "R I,Enrique Urien,143,H04440,P.S. B  ENRIQUE URIEN",
                "R I,La Tigra,77,H02857,P.S. A  LA TIGRA",
                "R I,Chorotis,66,H04447,P.S. A  CHOROTIS",
                "R I,Villa Angela,125,H20000,C.SALUD SAN CAYETANO VILLA ANGELA",
                "R I,Villa Angela,124,H02855,C.SALUD DRES. BRAVERMAN",
                "R I,Villa Angela,123,H04443,P.S. B  EL PORVENIR - DR. A. OTAÑO",
                "R I,San Bernardo,29,H04429,HOSP. SAN BERNARDO - DR. INGENIEROS",
                "R I,Santa Sylvina,28,H04445,HOSP. SANTA SYLVINA - F. JUSTO SANTA MARIA",
                "R I,Coronel Du Graty,27,H04444,HOSP. CORONEL DU GRATY",
                "R I,Villa Angela,25,H04435,HOSP. DR. SALVADOR MAZZA - VILLA ANGELA",
                "R II,Quitilipi,83,H02861,P.S. A  EL PALMAR",
                "R II,Machagai,75,H02860,P.S. A  COLONIA ABORIGEN",
                "R II,Colonia Elisa,16,H04375,HOSP. COLONIA ELISA - SARGENTO CABRAL",
                "R II,Capitan Solari,58,H02879,P.S. A  CAPITAN SOLARI",
                "R II,,,H04419,CENTRO DE SALUD VICTORIO DIAS",
                "R II,Pres. de la Plaza,4,H04426,HOSP. PRES. DE LA PLAZA - DR. R. MEJIAS",
                "R II,Quitilipi,1,H04418,HOSP. QUITILIPI - DR. E. RODRIGUEZ",
                "R II,Las Garcitas,8,H04450,HOSP. LAS GARCITAS - DR. J. ARCE",
                "R II,Villa Berthet,26,H04432,HOSP. VILLA BERTHET - DR. P. SALICA",
                "R II,Machagai,22,H04425,HOSP. MACHAGAI - DR. DIAZ Y PEREYRO",
                "R III,Cote Lai,55,H02880,P.S. A  COTE-LAI",
                "R III,Las Palmas,72,H02846,C.SALUD LAS PALMAS",
                "R III,Colonia Popular,118,H02854,P.S. A  COLONIA POPULAR",
                "R III,Basail,54,H02864,P.S. A  BASAIL",
                "R III,Laguna Blanca,,H04394,P.S. A LAGUNA BLANCA",
                "R III,Puerto Tirol,10,H04390,HOSP. PUERTO TIROL - DR. C. OXLEY",
                "R III,Puerto Bermejo,15,H04910,HOSP. PUERTO BERMEJO - DR. E. MERLO",
                "R III,Lapachito,56,H02848,P.S. A  LAPACHITO",
                "R III,Puerto Eva Perón,95,H02847,P.S. B  EVA PERON",
                "R III,Charadai,14,H04396,HOSP. CHARADAI",
                "R III,Makalle,5,H04384,HOSP. MAKALLE - GRAL. DONOVAN",
                "R III,La Escondida,12,H04383,HOSP. LA ESCONDIDA",
                "R III,Las Palmas,145,H91307,HOSPITAL DE LA HERMANDAD ARGENTINO PARAGUAYO - LAS PALMAS",
                "R III,La Verde,13,H04382,HOSP. LA VERDE - DR. A. RODRIGUEZ",
                "R III,Resistencia,112,H02877,C.SALUD EX OBRADOR PTE.GRAL.BELGRANO",
                "R III,Margarita Belén,53,H02859,P.S. A  MARGARITA BELEN",
                "R III,General Vedia,6,H04911,HOSP. GENERAL VEDIA",
                "R III,Isla del Cerrito,74,H02845,P.S. A  ISLA DEL CERRITO",
                "R III,La Leonesa,9,H04364,HOSP. LA LEONESA - DR. A. FLEMING",
                "R III,Colonia Benitez,52,H02858,P.S. A  COLONIA BENITEZ",
                "R IV,Hermoso Campo,62,H04500,HOSP. HERMOSO CAMPO - DR. ZENO",
                "R IV,Corzuela,61,H04486,HOSP. CORZUELA - GRAL. BELGRANO",
                "R IV,Gancedo,80,H04499,P.S. A  GANCEDO",
                "R IV,General Pinedo,36,H04498,HOSP. GRAL. PINEDO - DR. I. WAISMAN",
                "R IV,Las Breñas,140,H04489,C.SALUD LAS BREÑAS",
                "R IV,Las Breñas,34,H04487,HOSP. LAS BREÑAS - 9 DE JULIO",
                "R IV,Charata,35,H04490,HOSP. CHARATA - DR. E. DE LLAMAS",
                "R V,El Sauzalito,68,H04511,P.S. A  EL SAUZAL",
                "R V,El Sauzalito,69,H04512,P.S. B  WICHI o EL PINTADO",
                "R V,Juan José Castelli,131,H04585,P.S. B  BARRIO NOCAYI",
                "R V,Villa Rio Bermejito,81,H04534,P.S. A  EL ESPINILLO",
                "R V,Fuerte Esperanza,67,H04513,P.S. A  COMANDANCIA FRIAS",
                "R V,,,H00014,CENTRO DE SALUD JUNTA UNIDAD MISIONERA (JUN)",
                "R V,Fuerte Esperanza,82,H04527,P.S. A  FUERTE ESPERANZA",
                "R V,Juan José Castelli,130,H04599,P.S. B  SAN ANTONIO - JUAN JOSÉ CASTELLI",
                "R V,,,H00016,CENTRO DE SALUD VACUNATORIO",
                "R V,Villa Río Bermejito,79,H04547,P.S. A  VILLA RIO BERMEJITO",
                "R V,,,H00015,CENTRO DE SALUD CHACRA 108",
                "R V,,,H00013,CENTRO DE SALUD BARRIO CURISHI",
                "R V,El Sauzalito,64,H04501,HOSP. EL SAUZALITO - DR. ARTURO ILLIA",
                "R V,Juan José Castelli,129,H04575,P.S. B  BARRIO SARMIENTO",
                "R V,Nueva Pompeya,63,H04519,HOSP. NUEVA POMPEYA",
                "R V,Juan José Castelli,37,H04572,HOSP. JUAN JOSE CASTELLI - GRAL GÜEMES",
                "R V,Tres Isletas,141,H20002,C.SALUD BARRIO NORTE",
                "R V,Miraflores,57,H04552,P.S. A  MIRAFLORES",
                "R V,Tres Isletas,142,H09866,C.S. NUEVA ALIANZA",
                "R V,Tres Isletas,38,H04603,HOSP. TRES ISLETAS",
                "R V,Tres Isletas,,H00025,REGION SANITARIA V",
                "R VI,,,H00017,C.S. COMUNIDAD TERAPEUTICA LA EDUVIGIS",
                "R VI,Pampa Almirón,65,H02852,P.S. A  PAMPA ALMIRON",
                "R VI,La Eduvigis,78,H02851,P.S. A  LA EDUVIGIS",
                "R VI,,,H91344,C. SALUD NUESTRA SRA DE LA PAZ",
                "R VI,Ciervo Petiso,73,H02850,P.S. A  CIERVO PETISO",
                "R VI,Colonias Unidas,11,H04451,HOSP. COLONIAS UNIDAS - BERNARDINO RIVADAVIA",
                "R VI,,,H91345,C. SALUD BARRIO PANIAGUA",
                "R VI,General San Martín,127,H04477,P.S. B  BARRIO TOBA",
                "R VI,General San Martín,126,H04472,P.S. B  BARRIO LEALE",
                "R VI,Laguna Limpia,33,H04470,HOSP. LAGUNA LIMPIA - C. HARVEY",
                "R VI,General San Martín,31,H04471,HOSP. GENERAL SAN MARTIN - DR. PERTILE",
                "R VI,La Eduvigis,84,H02853,P.S. A  SELVA RIO ORO",
                "R VI,General San Martín,128,H04478,P.S. B  BARRIO ESPERANZA",
                "R VI,Pres. Roca,30,H04469,HOSP. PRESIDENCIA ROCA",
                "R VI,Pampa del Indio,32,H04452,HOSP. PAMPA DEL INDIO - DR. TARDELLI",
                "R VII,,,H91338,CENTRO INTEGRAL AMIGABLE ATENCION DEL ADOLENTE",
                "R VII,,,H00009,CENTRO DE SALUD SANTIAGO NUDELMANN",
                "R VII,Pres. Roque Saenz Peña,135,H20001,C.SALUD SAN CAYETANO PRES. ROQUE SAENZ PEÑA",
                "R VII,Pres. Roque Saenz Peña,139,H04411,P.S. B  SANTA RITA - 1º DE MAYO",
                "R VII,Pres. Roque Saenz Peña,134,H04408,P.S. B  BARRIO NAN-QUON-TOBA",
                "R VII,Los Frentones,116,H02843,P.S. A  RIO MUERTO",
                "R VII,Pres. Roque Saenz Peña,138,H04412,P.S. B  BARRIO J. D. PERON (SALA 71)",
                "R VII,Pres. Roque Saenz Peña,137,H04410,P.S. B  BARRIO OBRERO",
                "R VII,,,H00004,CENTRO DE SALUD BARRIO MITRE S PEÑA",
                "R VII,Pres. Roque Saenz Peña,133,H04414,P.S. B  BARRIO PUERTA DEL SOL",
                "R VII,Pres. Roque Saenz Peña,136,H04415,P.S. B  DR. EUSEBIO TABOADA - BARRIO LAMADR",
                "R VII,,,H00003,CENTRO DE SALUD NESTOR KIRCHNER",
                "R VII,,,H00010,CENTRO DE SALUD VICTORIA WAKS",
                "R VII,,,H00008,CENTRO DE SALUD SANTA MONICA",
                "R VII,Concepción del Bermejo,21,H04404,HOSP. CONCEPCION DEL BERMEJO",
                "R VII,Napenay,71,H02849,P.S. A  NAPENAY",
                "R VII,Pres. Roque Saenz Peña,132,H04409,P.S. B  BRAILLAR POCCARD - BARRIO SARMIENTO",
                "R VII,Avía Terai,18,H04405,HOSP. AVIA TERAI - DR. P. CHUTRO",
                "R VII,,,H91340,CENTRO DE SALUD  NALA  SAENZ PEÑA",
                "R VII,Campo Largo,24,H04406,HOSP. CAMPO LARGO - DR. E. SAGARDUY",
                "R VII,Los Frentones,23,H04398,HOSP. LOS FRENTONES",
                "R VII,Taco Pozo,20,H04397,HOSP. TACO POZO",
                "R VII,Pampa del Infierno,19,H04399,HOSP. PAMPA DEL INFIERNO - A. BROWN",
                "R VII,Pampa del Infierno,,H00026,DIRECCION REGION SANITARIA VII",
                "R VIII,Barranqueras,101,H00493,C.SALUD VILLA FORESTACION - BARRANQUERAS",
                "R VIII,,,cic001,SUB CENTRO GRINGO PINTO",
                "R VIII,Colonia Baranda,7,H04369,HOSP. COLONIA BARANDA",
                "R VIII,,,cic002,SUB CENTRO CIUDAD DE LOS MILAGROS",
                "R VIII,Resistencia,50,H02871,C.SALUD VILLA PEGORARO",
                "R VIII,Resistencia,99,H00607,C.SALUD VILLA DON ALBERTO",
                "R VIII,Resistencia,45,H02872,C.SALUD BARRIO TOBA",
                "R VIII,Resistencia,109,H02878,C.SALUD M.A.P.I.C.",
                "R VIII,Resistencia,89,H02868,C.SALUD BARRIO SANTA INES",
                "R VIII,,,cic003,SUB CENTRO SAN FERNANDO EMMANUEL",
                "R VIII,Resistencia,40,H02866,C.SALUD VILLA GRAL. BELGRANO",
                "R VIII,Resistencia,39,H00894,C.SALUD VILLA SAAVEDRA - INMACULADA CONCEPCION",
                "R VIII,Fontana,41,H00604,C.SALUD VILLA ELBA",
                "R VIII,Resistencia,100,H02875,C.SALUD LOTE 203",
                "R VIII,Resistencia,117,H00653,C.SALUD LOTE 202 - DR. MARADONA",
                "R VIII,Resistencia,108,H00609,C.SALUD BARRIO ESPAÑA",
                "R VIII,Resistencia,86,H02870,C.SALUD VILLA LOS LIRIOS",
                "R VIII,,,H00018,C.S. PRESIDENTE NESTOR KIRCHNER",
                "R VIII,Barranqueras,46,H02862,C.SALUD BARRIO LAS MALVINAS - 4 DE JUNIO",
                "R VIII,Barranqueras,104,H00877,C.SALUD DR. PEDRO BIOLCHI - SAN FERNANDO",
                "R VIII,Resistencia,92,H00610,C.SALUD SANTA CATALINA",
                "R VIII,Resistencia,115,H00896,C.SALUD VILLA ODORICO",
                "R VIII,Fontana,90,H00424,C.SALUD RIO ARAZA",
                "R VIII,Resistencia,87,H00649,C.SALUD BARRIO GOLF CLUB",
                "R VIII,Resistencia,102,H00648,C.SALUD VILLA URQUIZA",
                "R VIII,Resistencia,96,H00425,C.SALUD VILLA DON ANDRES",
                "R VIII,Resistencia,42,H00893,C.SALUD VILLA LUZURIAGA - DRA. MICUCCI",
                "R VIII,Fontana,110,H00603,C.SALUD CACIQUE PELAYO (FONTANA)",
                "R VIII,Resistencia,88,H02867,C.SALUD VILLA SAN MARTIN",
                "R VIII,Fontana,43,H00613,C.SALUD FONTANA",
                "R VIII,Resistencia,94,H00620,C.SALUD VILLA BARBERAN",
                "R VIII,Resistencia,120,H02874,SUBC.SAL. SANTA RITA",
                "R VIII,Resistencia,111,H00892,C.SALUD VILLA GHIO - DR. A. LOTTERO",
                "R VIII,Resistencia,85,H00895,C.SALUD VILLA RIO NEGRO",
                "R VIII,Resistencia,48,H02873,C.SALUD VILLA ALVEAR",
                "R VIII,Resistencia,113,H02876,C.SALUD VILLA CRISTO REY",
                "R VIII,Resistencia,105,H00624,C.SALUD GENERAL OBLIGADO",
                "R VIII,Barranqueras,114,H00876,C.SALUD LA TOMA (BARRANQUERAS)",
                "R VIII,Puerto Vilelas,98,H00614,C.SALUD PUERTO VILELAS - DR. FINOCHIETTO",
                "R VIII,Barranqueras,93,H00622,C.SALUD BARRIO 500 VIVIENDAS - DR. R. CARRILLO",
                "R VIII,Resistencia,91,H02869,C.SALUD EL TALA",
                "R VIII,,,H00155,DIRECCION DE ENFERMERIA",
                "R VIII,Resistencia,97,H00606,C.SALUD BARRIO GUIRALDES",
                "R VIII,Barranqueras,106,H00878,C.SALUD VILLA HORTENSIA (BARRANQUERAS)",
                "R VIII,,,H00494,HOSPITAL EVA PERON BARRANQUERAS",
                "R VIII,Resistencia,47,H00608,C.SALUD VILLA LIBERTAD - A. M. DE JUSTO",
                "SUBSEC,,,H91339,CENTRO DERMATOLOGICO DR MANUEL GIMENEZ",
                "SUBSEC,,,H00021,DIRECCION DE EPIDEMIOLOGIA",
                "SUBSEC,,,H00020,CENTRO DE REF. EN CONS. PROBLEMATICOS",
                "SUBSEC,,,H00002,DIRECCION DE ODONTOLOGIA",
                "SUBSEC,,,H00887,C. SALUD MENTAL LA LOMITA BQUERAS",
                "SUBSEC,,,H91343,CENTRO DE SALUD MENTAL Y ADICCIONES DON ORIONE",
                "SUBSEC,Resistencia,49,H20006,C.S. SANTA APOLONIA",
                "SUBSEC,,,H20004,HOSPITAL ODONTOLOGICO",
                "SUBSEC,Pres. Roque Saenz Peña,17,H04407,HOSP. ROQUE SAENZ PEÑA - 4 DE JUNIO",
                "SUBSEC,Resistencia,,H00000,MATERNIDAD Y INFANCIA",
                "SUBSEC,Resistencia,3,H04366,HOSP. PEDIATRICO - DR. A. CASTELAN",
                "SUBSEC,Resistencia,,H91309,UNIDAD DE COORDINACION DE EMERGENCIAS MEDICAS",
                "SUBSEC,Resistencia,60,H20005,LABORATORIO CENTRAL",
                "SUBSEC,,,H00022,DIRECCION DE LABORATORIO",
                "SUBSEC,Resistencia,2,H00897,HOSP. JULIO C. PERRANDO",
        };



            for (String rowData : data) {
                String[] fields = rowData.split(",");
                RegionEnum regionEnum = regionMap.get(fields[0]);
                Region region = regionRepository.findByRegionEnum(regionEnum);
                if (region == null) {
                    throw new ResourceNotFoundException("No se encontró Region para: " + fields[0]);
                }
                String localidad = fields[1];
                String codRecupero = fields[2];
                String cuie = fields[3];
                String nombre = fields[4];
                Efector efector = new Efector();
                efector.setRegion(region);
                efector.setLocalidad(localidad);
                efector.setCodRecupero(codRecupero);
                efector.setCuie(cuie);
                efector.setNombre(nombre);
                efector.setRegistros(new ArrayList<>());
                efector.setExpedientes(new ArrayList<>());
                Auditor auditor = new Auditor(LocalDate.now(), LocalDate.now());
                efector.setAuditor(auditor);
                efectorRepository.save(efector);
            }
        }


    }
